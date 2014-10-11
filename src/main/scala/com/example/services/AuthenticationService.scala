package com.example.services

import com.example.models.AuthenticationToken
import scala.concurrent.Future

trait AuthenticationService {

  def getNewAuthenticationToken: Future[AuthenticationToken]
  def getSessionId(requestToken: String): Future[Option[String]]
  
}