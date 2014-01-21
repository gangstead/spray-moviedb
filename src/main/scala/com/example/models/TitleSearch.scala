package com.example.models

import spray.json.DefaultJsonProtocol
import scala.language.implicitConversions

case class TitleSearchQuery(val query: String, val page: Int = 1) extends SearchQuery

case class TitleSearchResultsMovieDb(val page: Long, 
    val results: List[MovieDb],
    val total_pages: Long,
    val total_results: Long)
    
case class TitleSearchResults(val page: Long, 
    val results: List[Movie],
    val total_pages: Long,
    val total_results: Long)

object TitleSearchImplicits {
  import com.example.models.MovieImplicits._
  implicit def dbToModel(t: TitleSearchResultsMovieDb): TitleSearchResults = {
    TitleSearchResults(t.page, t.results.map(MovieDbToMovie(_)), t.total_pages, t.total_results)
  }
  implicit def dbToModel (o: Option[TitleSearchResultsMovieDb]): Option[TitleSearchResults] = o.map(dbToModel(_))
}
    
object TitleSearchJsonProtocol extends DefaultJsonProtocol{
    import MovieJsonProtocol._
    implicit val titleSearchFormat = jsonFormat4(TitleSearchResults)
    implicit val titleSearchMovieDbFormat = jsonFormat4(TitleSearchResultsMovieDb)
}