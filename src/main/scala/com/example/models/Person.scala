package com.example.models

import spray.json.DefaultJsonProtocol

case class Person(val id: Long,
    val name: Option[String],
    val birthday: Option[String],
    val deathday: Option[String],
    val place_of_birth: Option[String],
    val biography: Option[String],
    val profile_path: Option[String])
    
object PersonJsonProtocol extends DefaultJsonProtocol{
    implicit val personFormat = jsonFormat7(Person)
}    
