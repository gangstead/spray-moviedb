package com.example.models

import spray.json.DefaultJsonProtocol

case class PersonSearchQuery(val query: String, val page: Int = 1) extends SearchQuery

case class PersonSearchResults(val page: Long, 
    val results: List[Person],
    val total_pages: Long,
    val total_results: Long)
    
object PersonSearchJsonProtocol extends DefaultJsonProtocol{
    import PersonJsonProtocol._
    implicit val personSearchFormat = jsonFormat4(PersonSearchResults)
}