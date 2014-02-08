package com.example.routes

import spray.http.StatusCodes
import spray.httpx.UnsuccessfulResponseException
import spray.httpx.SprayJsonSupport._
import spray.routing.Directive._
import spray.routing.ExceptionHandler
import spray.routing.HttpService
import spray.util.LoggingContext

trait RouteExceptionHandlers extends HttpService {
  
  implicit def movieExceptionHandler(implicit log: LoggingContext) = ExceptionHandler {
    case e: UnsuccessfulResponseException =>
      requestUri { uri =>
        log.warning("Request to {} could not be handled normally", uri)
        complete(e.response.status)
      }
    case t: Throwable =>
      requestUri { uri =>
        log.warning("Request to {} could not be handled normally", uri)
        complete(StatusCodes.InternalServerError, "Wouldn't you like to know what happened?" + t)
      }  
  }    

}