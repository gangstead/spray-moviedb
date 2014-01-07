package com.example.models

import spray.json.DefaultJsonProtocol

case class MovieDbSession(success: Boolean, session_id: String)

object MovieDbSessionJsonProtocol extends DefaultJsonProtocol{
    implicit val movieDbSessionFormat = jsonFormat2(MovieDbSession)
} 