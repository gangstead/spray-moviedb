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
import com.example.models.TitleSearchResults
import com.example.models.Movie

@Named
@Scope("prototype")
class MoviesRoute @Inject()(ms: MovieService, asb: ActorSystemBean) extends HttpServiceActor 
																	with ActorLogging
																	with RouteExceptionHandlers {
  
  import asb.system.dispatcher
  import com.example.models.MovieImplicits._
  import com.example.models.TitleSearchImplicits._
  
  def receive = runRoute {
    get {
      parameters('query, 'page ? 1).as(TitleSearchQuery) { query =>
	    val titleSearchResults : Option[TitleSearchResults] = ms.getTitleSearchResults(query)
	    complete(titleSearchResults) 
      }~
	  path(LongNumber) { movieId =>  
		val movie : Option[Movie] = ms.getMovie(movieId)
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