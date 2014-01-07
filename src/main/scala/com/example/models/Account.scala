package com.example.models

import spray.json.DefaultJsonProtocol

case class Account (id: Long, 
    include_adult: Option[Boolean], 
    name: Option[String], 
    username: Option[String])

object AccountJsonProtocol extends DefaultJsonProtocol{
    implicit val accountFormat = jsonFormat4(Account)
}