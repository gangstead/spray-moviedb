package com.example.services

import scala.collection.mutable.{ HashMap => MutableHashMap }
import scala.concurrent.Future

import com.example.config.ActorSystemBean
import com.example.models.AuthenticationToken
import com.example.models.AuthenticationTokenJsonProtocol._
import com.example.models.MovieDbSession
import com.example.models.MovieDbSessionJsonProtocol._
import com.example.util.GlobalConstants.API_KEY_QUERY_PARAM

import javax.inject.Inject
import javax.inject.Named
import spray.client.pipelining._
import spray.http.HttpHeaders.Host
import spray.http.HttpRequest
import spray.http.HttpResponse
import spray.http.StatusCodes
import spray.httpx.SprayJsonSupport._
import spray.json.pimpString

@Named
class AuthenticationServiceImpl @Inject() (asb: ActorSystemBean) extends AuthenticationService {

  import asb._ // make the implicit ActorSystem available for sendRecieve
  import asb.system.dispatcher // execution context for futures below 

  private var requestTokenSessionIdMap = new MutableHashMap[String, Option[String]]()

  private def defaultRequest = { request: HttpRequest =>
    request.withEffectiveUri(false, Host("api.themoviedb.org", 80)) ~> sendReceive
  }

  def getNewAuthenticationToken: Future[AuthenticationToken] = {
    val pipeline = defaultRequest ~> unmarshal[AuthenticationToken]
    val responseFuture = pipeline {
      Get("/3/authentication/token/new" + API_KEY_QUERY_PARAM)
    }
    responseFuture
  }

  private def getNewSession(requestToken: String) = {
    val pipeline = defaultRequest
    val responseFuture = pipeline {
      Get("/3/authentication/session/new" + API_KEY_QUERY_PARAM + "&request_token=" + requestToken)
    }

    val sessionId = responseFuture map { response =>
      response match {
        case res @ HttpResponse(StatusCodes.OK, _, _, _) => {
          val session = res.entity.asString.asJson.convertTo[MovieDbSession]
          requestTokenSessionIdMap.put(requestToken, Some(session.session_id))
          Some(session.session_id)
        }
        case _ => None
      }
    }
    sessionId
  }

  def getSessionId(requestToken: String): Future[Option[String]] = {
    requestTokenSessionIdMap.get(requestToken) match {
      case Some(existingSession) => Future(existingSession)
      case None => getNewSession(requestToken)
    }
  }
}