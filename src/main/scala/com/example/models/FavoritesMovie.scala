package com.example.models

import spray.json.DefaultJsonProtocol

case class FavoritesMovie(id: Long, 
    adult: Option[Boolean],
    backdrop_path: Option[String],
    original_title: Option[String],
    release_date: Option[String],
    poster_path: Option[String],
    popularity: Option[Double],
    title: Option[String],
    vote_average: Option[Double],
    vote_count: Option[Long])
    
object FavoritesMovieJsonProtocol extends DefaultJsonProtocol{
    implicit val favoritesMovieFormat = jsonFormat10(FavoritesMovie)
}    