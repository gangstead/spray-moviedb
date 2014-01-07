package com.example.models

import spray.json.DefaultJsonProtocol

case class TitleSearchQuery(val query: String, val page: Int = 1) extends SearchQuery

case class TitleSearchResults(val page: Long, 
    val results: List[Movie],
    val total_pages: Long,
    val total_results: Long)
    
object TitleSearchJsonProtocol extends DefaultJsonProtocol{
    import MovieJsonProtocol._
    implicit val titleSearchFormat = jsonFormat4(TitleSearchResults)
}