package com.example.config

import akka.actor._
import akka.pattern.ask
import akka.util.Timeout

import com.example.routes._

import javax.annotation.PreDestroy
import javax.inject.{ Inject, Named }

import org.springframework.context.ApplicationContext

/**
 * An Akka Extension which holds the ApplicationContext for creating actors from bean templates.
 */
object SpringExt extends ExtensionKey[SpringExt]
class SpringExt(system: ExtendedActorSystem) extends Extension {
  @volatile var ctx: ApplicationContext = _
}

/**
 * A bean representing actor-based services, wrapping an ActorSystem.
 */
@Named
class ActorSystemBean @Inject() (ctx: ApplicationContext) {

  implicit val system = ActorSystem("moviedb")

  /**
   * This stores the ApplicationContext within the ActorSystem’s Spring
   * extension for later use.
   */
  SpringExt(system).ctx = ctx

  lazy val moviesRoute = system.actorOf(Props(SpringExt(system).ctx.getBean(classOf[MoviesRoute])))
  lazy val peopleRoute = system.actorOf(Props(SpringExt(system).ctx.getBean(classOf[PeopleRoute])))
  lazy val loginRoute = system.actorOf(Props(SpringExt(system).ctx.getBean(classOf[LoginRoute])))
  lazy val accountRoute = system.actorOf(Props(SpringExt(system).ctx.getBean(classOf[AccountRoute])))  
  lazy val apiRouter = system.actorOf(Props(SpringExt(system).ctx.getBean(classOf[ApiRouter])))

  @PreDestroy
  def shutdown(): Unit = system.shutdown()
}