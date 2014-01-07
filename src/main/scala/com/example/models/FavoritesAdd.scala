package com.example.models

import spray.json.DefaultJsonProtocol

case class FavoritesAdd(movie_id: Long, favorite: Boolean = true) {
  require(movie_id > 0, "movie_id must be greater than 0")
}

object FavoritesAddJsonProtocol extends DefaultJsonProtocol{
    implicit val favoritesAddFormat = jsonFormat2(FavoritesAdd)
}