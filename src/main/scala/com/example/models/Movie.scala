package com.example.models

import spray.json._
import scala.language.implicitConversions

//        {
//            "adult": false,
//            "backdrop_path": "/mGN0lH2phYfesyEVqP2xvGUaxAQ.jpg",
//            "id": 101,
//            "original_title": "Léon",
//            "release_date": "1994-09-14",
//            "poster_path": "/uzKHnyOAPfHsoqN8BFKgIVtdBzF.jpg",
//            "popularity": 8.86884949131652,
//            "title": "Leon: The Professional",
//            "vote_average": 7.6,
//            "vote_count": 967
//        }

case class Language(val name: String)
case class Genre(val id: Long, val name: String)
case class Movie(val id: Long,
    val title: String,
    val releaseDate: Option[String],
    val posterPathW92: Option[String],
    val posterPathW185: Option[String],
    val posterPathW500: Option[String],
    val overview: Option[String],
    val budget: Option[Long],
    val genres: Option[List[Genre]],
    val revenue: Option[Long],
    val runtime: Option[Long],
    val tagline: Option[String],
    val spokenLanguages: Option[List[Language]])
    
case class MovieDb(val id: Long,
    val title: String,
    val release_date: Option[String],
    val poster_path: Option[String],
    val overview: Option[String],
    val budget: Option[Long],
    val genres: Option[List[Genre]],
    val revenue: Option[Long],
    val runtime: Option[Long],
    val tagline: Option[String],
    val spoken_languages: Option[List[Language]])
    
object MovieConstants {
  val POSTER_PATH_PREFIX_W92 = "http://d3gtl9l2a4fn1j.cloudfront.net/t/p/w92"
  val POSTER_PATH_PREFIX_W185 = "http://d3gtl9l2a4fn1j.cloudfront.net/t/p/w185"
  val POSTER_PATH_PREFIX_W500 = "http://d3gtl9l2a4fn1j.cloudfront.net/t/p/w500"
}

object MovieImplicits {
  implicit def MovieDbToMovie(m: MovieDb): Movie = {
    Movie(m.id,
        m.title,
        m.release_date,
        m.poster_path.map(MovieConstants.POSTER_PATH_PREFIX_W92 + _),
        m.poster_path.map(MovieConstants.POSTER_PATH_PREFIX_W185 + _),
        m.poster_path.map(MovieConstants.POSTER_PATH_PREFIX_W500 + _),
        m.overview,
        m.budget,
        m.genres,
        m.revenue,
        m.runtime,
        m.tagline,
        m.spoken_languages
    )}
  implicit def MovieDbOptionToMovieOption(mo: Option[MovieDb]): Option[Movie] = mo.map(MovieDbToMovie(_))
}

object MovieJsonProtocol extends DefaultJsonProtocol {

  implicit val genresFormat = jsonFormat2(Genre)
  implicit val languageFormat = jsonFormat1(Language)
  implicit val MovieFormat = jsonFormat13(Movie)
  implicit val MovieDbFormat = jsonFormat11(MovieDb)
}
	