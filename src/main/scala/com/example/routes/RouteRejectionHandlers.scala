package com.example.routes

import spray.http.StatusCodes
import spray.http.Uri.apply
import spray.routing.AuthorizationFailedRejection
import spray.routing.HttpService
import spray.routing.MissingCookieRejection
import spray.routing.RejectionHandler

trait RouteRejectionHandlers extends HttpService {
  
  implicit val myRejectionHandler = RejectionHandler {
    case MissingCookieRejection(_) :: _ => redirect("/login", StatusCodes.TemporaryRedirect)
    case AuthorizationFailedRejection :: _ => redirect("/login", StatusCodes.TemporaryRedirect)
  } 

}