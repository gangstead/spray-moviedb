package com.example.models

import spray.json._

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
    val posterPath: Option[String],
    val overview: Option[String],
    val budget: Option[Long],
    val genres: Option[List[Genre]],
    val revenue: Option[Long],
    val runtime: Option[Long],
    val tagline: Option[String],
    val spokenLanguages: Option[List[Language]])
    
object Movie {
  val POSTER_PATH_PREFIX_W92 = "http://d3gtl9l2a4fn1j.cloudfront.net/t/p/w92"
  val POSTER_PATH_PREFIX_W185 = "http://d3gtl9l2a4fn1j.cloudfront.net/t/p/w185"
  val POSTER_PATH_PREFIX_W500 = "http://d3gtl9l2a4fn1j.cloudfront.net/t/p/w500"
}

object MovieJsonProtocol extends DefaultJsonProtocol {

  implicit val genresFormat = jsonFormat2(Genre)
  implicit val languageFormat = jsonFormat1(Language)
  
  implicit object MovieJsonFormat extends RootJsonFormat[Movie] {
    def write(m: Movie) = JsObject(
      "id" -> JsNumber(m.id),
      "title" -> JsString(m.title),
      "releaseDate" -> m.releaseDate.map(JsString(_)).getOrElse(JsNull),
      "posterPathW92" -> m.posterPath.map(p => JsString(Movie.POSTER_PATH_PREFIX_W92 + p)).getOrElse(JsNull),
      "posterPathW185" -> m.posterPath.map(p => JsString(Movie.POSTER_PATH_PREFIX_W185 + p)).getOrElse(JsNull),
      "posterPathW500" -> m.posterPath.map(p => JsString(Movie.POSTER_PATH_PREFIX_W500 + p)).getOrElse(JsNull),
      "overview" -> m.overview.map(JsString(_)).getOrElse(JsNull),
      "budget" -> m.budget.map(JsNumber(_)).getOrElse(JsNull),
      "genres" -> m.genres.map(l => JsArray(l.map(_.toJson))).getOrElse(JsNull),
      "revenue" -> m.revenue.map(JsNumber(_)).getOrElse(JsNull),
      "runtime" -> m.runtime.map(JsNumber(_)).getOrElse(JsNull),
      "tagline" -> m.tagline.map(JsString(_)).getOrElse(JsNull),
      "spokenLanguages" -> m.spokenLanguages.map(l => JsArray(l.map(_.toJson))).getOrElse(JsNull)
    )
    
    def read(value: JsValue) = {
      val fields = value.asJsObject.fields
      new Movie( fields.getOrElse("id", JsString("-1")).convertTo[Long],
          fields.getOrElse("title", JsString("err no title")).convertTo[String],
          fields.get("release_date").map(_.convertTo[String]),
          fields.get("poster_path").map(_.convertTo[String]),
          fields.get("overview").map(_.convertTo[String]),
          fields.get("budget").map(_.convertTo[Long]),
          fields.get("genres").map(_.convertTo[List[Genre]]),
          fields.get("revenue").map(_.convertTo[Long]),
          fields.get("runtime").map(_.convertTo[Long]),
          fields.get("tagline").map(_.convertTo[String]),
          fields.get("spoken_languages").map(_.convertTo[List[Language]]))
    }
  }
}
	