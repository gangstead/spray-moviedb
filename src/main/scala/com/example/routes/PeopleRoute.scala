package com.example.routes

import org.springframework.context.annotation.Scope

import com.example.config.ActorSystemBean
import com.example.models.PersonSearchJsonProtocol._
import com.example.models.PersonSearchQuery
import com.example.services.PersonService

import akka.actor.ActorLogging
import javax.inject.Inject
import javax.inject.Named
import spray.httpx.SprayJsonSupport._
import spray.routing.HttpServiceActor

@Named
@Scope("prototype")
class PeopleRoute @Inject()(ps: PersonService, asb: ActorSystemBean) extends HttpServiceActor 
											   with ActorLogging
											   with RouteExceptionHandlers{
  import asb.system.dispatcher
  
  def receive = runRoute {
    get {
      parameters('query, 'page ? 1).as(PersonSearchQuery) { query =>
	      val searchResults = ps.getPersonSearchResults(query)
	      complete(searchResults)           
      }
    }  
  }      
  
}