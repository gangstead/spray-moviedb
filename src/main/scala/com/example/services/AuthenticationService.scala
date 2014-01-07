package com.example.services

import com.example.models.AuthenticationToken

trait AuthenticationService {

  def getNewAuthenticationToken: AuthenticationToken
  def getSessionId(requestToken: String): Option[String]
  
}