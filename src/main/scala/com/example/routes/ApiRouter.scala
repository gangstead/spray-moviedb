package com.example.routes

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

import org.springframework.context.annotation.Scope

import com.example.config.ActorSystemBean

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.actorRef2Scala
import javax.inject.Inject
import javax.inject.Named
import spray.routing.Directive.pimpApply
import spray.routing.HttpService
import spray.routing.directives.CachingDirectives._


@Named
@Scope("prototype")
class ApiRouter @Inject()(asb: ActorSystemBean) extends Actor 
												with HttpService 
												with ActorLogging{

  def actorRefFactory = context
  
  val simpleCache = routeCache(maxCapacity = 1000, timeToIdle = 30 minutes)

  def receive = runRoute {
    compressResponseIfRequested(){
      alwaysCache(simpleCache) {
        pathPrefix("movies") { ctx => asb.moviesRoute ! ctx } ~
        pathPrefix("people") { ctx => asb.peopleRoute ! ctx }
      } ~
      pathPrefix("login") { ctx => asb.loginRoute ! ctx } ~
      pathPrefix("account") { ctx => asb.accountRoute ! ctx }
    }
  }
  
}