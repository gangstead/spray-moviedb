package com.example.services

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

import com.example.config.ActorSystemBean
import com.example.models.PersonSearchJsonProtocol._
import com.example.models.PersonSearchQuery
import com.example.models.PersonSearchResults
import com.example.util.GlobalConstants.API_KEY_QUERY_PARAM

import javax.inject.Inject
import javax.inject.Named
import spray.client.pipelining._
import spray.http.HttpHeaders.Host
import spray.http.HttpRequest
import spray.httpx.SprayJsonSupport._

@Named
class PersonServiceImpl @Inject()(asb: ActorSystemBean) extends PersonService {
  
  import asb._ // make the implicit ActorSystem available for sendRecieve
  import asb.system.dispatcher // execution context for futures below 
 
  private def defaultRequest = { request: HttpRequest =>
    request.withEffectiveUri(false, Host("api.themoviedb.org", 80)) ~> sendReceive
  }
  
  def getPersonSearchResults(query: PersonSearchQuery): Option[PersonSearchResults] = {
    val pipeline = defaultRequest ~> unmarshal[Option[PersonSearchResults]]
    val responseFuture = pipeline {
	  Get("/3/search/person" + API_KEY_QUERY_PARAM + query.queryParamsAmpPrefix)
	}
	Await.result(responseFuture.mapTo[Option[PersonSearchResults]], 5 seconds)      
  }
  
}