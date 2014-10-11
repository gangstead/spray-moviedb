package com.example.routes

import org.springframework.context.annotation.Scope

import com.example.config.ActorSystemBean
import com.example.services.AuthenticationService
import com.example.util.GlobalConstants.MOVIEDB_AUTH_URL
import com.example.util.GlobalConstants.REQUEST_TOKEN

import akka.actor.ActorLogging
import javax.inject.Inject
import javax.inject.Named
import spray.http.HttpCookie
import spray.http.StatusCodes
import spray.routing.HttpServiceActor

@Named
@Scope("prototype")
class LoginRoute @Inject() (as: AuthenticationService, asb: ActorSystemBean) extends HttpServiceActor
  with ActorLogging
  with RouteExceptionHandlers {

  import asb.system.dispatcher

  def receive = runRoute {
    get(
      ctx => {
        val authTokenFuture = as.getNewAuthenticationToken
        authTokenFuture map { authToken => 
          setCookie(HttpCookie(REQUEST_TOKEN, content = authToken.request_token)) {
            redirect(MOVIEDB_AUTH_URL + authToken.request_token, StatusCodes.TemporaryRedirect)
          }.apply(ctx)
        }
      })
  }

}