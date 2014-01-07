package com.example.services

import com.example.models.Movie
import com.example.models.MovieCast
import com.example.models.TitleSearchQuery
import com.example.models.TitleSearchResults
import com.example.models.Trailers

trait MovieService {
  
  def getMovie(movieId: Long): Option[Movie]
  def getMovieCast(movieId: Long): Option[MovieCast]
  def getTrailers(movieId: Long): Option[Trailers]
  def getTitleSearchResults(query: TitleSearchQuery): Option[TitleSearchResults]

}