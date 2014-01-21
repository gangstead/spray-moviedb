package com.example.services

import com.example.models.MovieCast
import com.example.models.TitleSearchQuery
import com.example.models.Trailers
import com.example.models.TitleSearchResultsMovieDb
import com.example.models.MovieDb

trait MovieService {
  
  def getMovie(movieId: Long): Option[MovieDb]
  def getMovieCast(movieId: Long): Option[MovieCast]
  def getTrailers(movieId: Long): Option[Trailers]
  def getTitleSearchResults(query: TitleSearchQuery): Option[TitleSearchResultsMovieDb]

}