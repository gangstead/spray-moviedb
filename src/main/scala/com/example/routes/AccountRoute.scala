package com.example.routes

import org.springframework.context.annotation.Scope

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

import com.example.config.ActorSystemBean
import com.example.models.AccountJsonProtocol._
import com.example.models.FavoritesAdd
import com.example.models.FavoritesAddJsonProtocol._
import com.example.models.FavoritesQuery
import com.example.models.FavoritesQueryJsonProtocol._
import com.example.services.AccountService
import com.example.services.AuthenticationService
import com.example.util.GlobalConstants.REQUEST_TOKEN

import akka.actor.ActorLogging
import javax.inject.Inject
import javax.inject.Named
import spray.http.HttpCookie
import spray.http.StatusCodes
import spray.httpx.SprayJsonSupport._
import spray.routing.HttpServiceActor

@Named
@Scope("prototype")
class AccountRoute @Inject()(as: AuthenticationService, 
							accountService: AccountService, 
							asb: ActorSystemBean) extends HttpServiceActor 
												  with ActorLogging
												  with RouteRejectionHandlers 
												  with RouteExceptionHandlers {
  
  import asb.system.dispatcher
  
  private def getSessionForRequestToken(requestToken: HttpCookie) = {
    as.getSessionId(requestToken.content)
  }
  
  private def sessionDefined(sessionId: Option[String]) = { sessionId.isDefined }
  
  def receive = runRoute {
    cookie(REQUEST_TOKEN) { token =>
      val sessionId = Await.result(getSessionForRequestToken(token), 5 seconds)
      authorize(sessionDefined(sessionId)){
        get {
          pathEnd {
            complete(accountService.getAccount(sessionId.get))
          }~
          path("favorites"){
            parameters('page ? 1, 'sortOrder ? "asc").as(FavoritesQuery) { query =>
	          complete(accountService.getAccountFavorites(query, sessionId.get)) 
      	    }
          }          
        }~
        post {
          path("favorites"){
            entity(as[FavoritesAdd]) { favoritesAdd =>
              accountService.addAccountFavorite(favoritesAdd, sessionId.get)
              complete(StatusCodes.Created)
            }
          }
        }
      }    
    }
  }

}