package com.example.services

import com.example.models.Movie
import com.example.models.MovieCast
import com.example.models.TitleSearchQuery
import com.example.models.TitleSearchResults
import com.example.models.Trailers
import scala.concurrent.Future

trait MovieService {
  
  def getMovie(movieId: Long): Future[Option[Movie]]
  def getMovieCast(movieId: Long): Future[Option[MovieCast]]
  def getTrailers(movieId: Long): Future[Option[Trailers]]
  def getTitleSearchResults(query: TitleSearchQuery): Future[Option[TitleSearchResults]]

}