package com.example.services

import scala.concurrent.Future

import com.example.config.ActorSystemBean
import com.example.models.Account
import com.example.models.AccountJsonProtocol._
import com.example.models.FavoritesAdd
import com.example.models.FavoritesQuery
import com.example.models.FavoritesQueryJsonProtocol._
import com.example.models.FavoritesQueryResults
import com.example.util.GlobalConstants.API_KEY_QUERY_PARAM

import javax.inject.Inject
import javax.inject.Named
import spray.client.pipelining._
import spray.http.FormData
import spray.http.HttpHeaders.Host
import spray.http.HttpRequest
import spray.http.HttpResponse
import spray.httpx.SprayJsonSupport._

@Named
class AccountServiceImpl @Inject()(asb: ActorSystemBean) extends AccountService {
  
  import asb._ // make the implicit ActorSystem available for sendRecieve
  import asb.system.dispatcher // execution context for futures below  
  
  private def defaultRequest = { request: HttpRequest =>
    request.withEffectiveUri(false, Host("api.themoviedb.org", 80)) ~> sendReceive
  }
  
  def getAccount(sessionId: String): Future[Account] = {
    val pipeline = defaultRequest ~> unmarshal[Account]
  	val responseFuture = pipeline {
  	  Get("/3/account" + API_KEY_QUERY_PARAM + "&session_id=" + sessionId)
  	}
    responseFuture   
  }
  
  def getAccountFavorites(favoritesQuery: FavoritesQuery, sessionId: String): Future[Option[FavoritesQueryResults]] = {
    val accountFuture = getAccount(sessionId)
    val pipeline = defaultRequest ~> unmarshal[Option[FavoritesQueryResults]]
   	val responseFuture = accountFuture flatMap {account => 
   	  pipeline {
    	  Get("/3/account/" + account.id +"/favorite_movies" + API_KEY_QUERY_PARAM + "&session_id=" + sessionId)
    	}
   	}
    responseFuture
  }
  
  def addAccountFavorite(favoritesAdd: FavoritesAdd, sessionId: String): Future[Unit] = {
    val accountFuture = getAccount(sessionId)
    val data = Map("movie_id" -> favoritesAdd.movie_id.toString, "favorite" -> favoritesAdd.favorite.toString)
    val pipeline = defaultRequest ~> unmarshal[HttpResponse]
    val responseFuture = accountFuture flatMap { account =>
      pipeline {
        Post("/3/account/" + account.id +"/favorite" + API_KEY_QUERY_PARAM + "&session_id=" + sessionId, FormData(data))
      }
    }
    responseFuture map { response =>
      response match {
        case HttpResponse(status, _, _, _) => {
          if (status.intValue >= 300) throw new RuntimeException("bah!")
        }
          
      }
    }
    
  }

}