package com.example.models

import spray.json.DefaultJsonProtocol

case class MovieCast(val cast: List[CastMember], val crew: List[CrewMember])
case class CastMember(val id: Long, val name: String, val character: String)
case class CrewMember(val id: Long, val name: String, val department: String, val job: String)

object MovieCastJsonProtocol extends DefaultJsonProtocol{
    implicit val castMemberFormat = jsonFormat3(CastMember)
    implicit val crewMemberFormat = jsonFormat4(CrewMember)
    implicit val movieCastFormat = jsonFormat2(MovieCast)
}