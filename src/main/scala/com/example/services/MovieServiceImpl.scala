package com.example.services

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps
import com.example.config.ActorSystemBean
import com.example.models.MovieCast
import com.example.models.MovieCastJsonProtocol._
import com.example.models.MovieJsonProtocol._
import com.example.models.TitleSearchJsonProtocol._
import com.example.models.TitleSearchQuery
import com.example.models.Trailers
import com.example.models.TrailersJsonProtocol._
import com.example.util.GlobalConstants.API_KEY_QUERY_PARAM
import javax.inject.Inject
import javax.inject.Named
import spray.client.pipelining._
import spray.http.HttpHeaders.Host
import spray.http.HttpRequest
import spray.httpx.SprayJsonSupport._
import com.example.models.MovieDb
import com.example.models.TitleSearchResultsMovieDb

@Named
class MovieServiceImpl @Inject()(asb: ActorSystemBean) extends MovieService {
  
  import asb._ // make the implicit ActorSystem available for sendRecieve
  import asb.system.dispatcher // execution context for futures 
  
  protected def defaultRequest = { request: HttpRequest =>
    request.withEffectiveUri(false, Host("api.themoviedb.org", 80)) ~> sendReceive
  }
  
  def getMovie(movieId: Long): Option[MovieDb] = {
	val pipeline = defaultRequest ~> unmarshal[Option[MovieDb]]
	val responseFuture = pipeline {
	  Get("/3/movie/" + movieId + API_KEY_QUERY_PARAM)
	}
	Await.result(responseFuture.mapTo[Option[MovieDb]], 5 seconds)
  }
  
  def getMovieCast(movieId: Long): Option[MovieCast] = {
    val pipeline = defaultRequest ~> unmarshal[Option[MovieCast]]
	val responseFuture = pipeline {
	  Get("/3/movie/" + movieId + "/casts" + API_KEY_QUERY_PARAM)
	}
	Await.result(responseFuture.mapTo[Option[MovieCast]], 5 seconds)
  }
  
  def getTrailers(movieId: Long): Option[Trailers] = {
    val pipeline = defaultRequest ~> unmarshal[Option[Trailers]]
	val responseFuture = pipeline {
	  Get("/3/movie/" + movieId + "/trailers" + API_KEY_QUERY_PARAM)
	}
	Await.result(responseFuture.mapTo[Option[Trailers]], 5 seconds)    
  }  
  
  def getTitleSearchResults(query: TitleSearchQuery): Option[TitleSearchResultsMovieDb] = {
    val pipeline = defaultRequest ~> unmarshal[Option[TitleSearchResultsMovieDb]]
    val responseFuture = pipeline {
	  Get("/3/search/movie" + API_KEY_QUERY_PARAM + query.queryParamsAmpPrefix)
	}
	Await.result(responseFuture.mapTo[Option[TitleSearchResultsMovieDb]], 5 seconds)      
  }
  
}