package com.example.routes

import org.springframework.context.annotation.Scope

import com.example.config.ActorSystemBean
import com.example.models.MovieJsonProtocol._
import com.example.models.MovieCastJsonProtocol._
import com.example.models.TitleSearchQuery
import com.example.models.TitleSearchJsonProtocol._
import com.example.models.TrailersJsonProtocol._
import com.example.services.MovieService

import akka.actor.ActorLogging
import javax.inject.Inject
import javax.inject.Named
import spray.httpx.SprayJsonSupport._
import spray.routing.HttpServiceActor

@Named
@Scope("prototype")
class MoviesRoute @Inject()(ms: MovieService, asb: ActorSystemBean) extends HttpServiceActor 
																	with ActorLogging
																	with RouteExceptionHandlers {
  
  import asb.system.dispatcher
  
  def receive = runRoute {
    get {
      parameters('query, 'page ? 1).as(TitleSearchQuery) { query =>
	    val titleSearchResults = ms.getTitleSearchResults(query)
	    complete(titleSearchResults) 
      }~
	  path(LongNumber) { movieId =>  
		val movie = ms.getMovie(movieId)
		complete(movie)
	  }~
	  path(LongNumber / "cast") { movieId =>
	    val movieCast = ms.getMovieCast(movieId)
	    complete(movieCast)      
	  }~
	  path(LongNumber / "trailers") { movieId =>
	    val trailers = ms.getTrailers(movieId)
	    complete(trailers)     
	  }        
    }
  }      
  
}