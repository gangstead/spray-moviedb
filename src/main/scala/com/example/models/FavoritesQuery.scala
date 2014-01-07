package com.example.models

import spray.json.DefaultJsonProtocol

case class FavoritesQuery(page: Int = 1, sortOrder: String = "asc") {
  require(page > 0, "page must be greater than 0")
  require( (sortOrder == "asc") || (sortOrder == "desc"), "valid sortOrder values are 'asc' or 'desc'")
}

case class FavoritesQueryResults(val page: Long, 
    val results: List[FavoritesMovie],
    val total_pages: Long,
    val total_results: Long)
    
object FavoritesQueryJsonProtocol extends DefaultJsonProtocol{
    import FavoritesMovieJsonProtocol._
    implicit val favoritesQueryFormat = jsonFormat4(FavoritesQueryResults)
}