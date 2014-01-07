package com.example.models

import spray.json.DefaultJsonProtocol

case class AuthenticationToken(val success: Boolean, val expires_at: String, val request_token: String)

object AuthenticationTokenJsonProtocol extends DefaultJsonProtocol{
    implicit val authenticationTokenFormat = jsonFormat3(AuthenticationToken)
}