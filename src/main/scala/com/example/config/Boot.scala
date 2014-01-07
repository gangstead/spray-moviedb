package com.example.config

import org.springframework.context.support.ClassPathXmlApplicationContext

import spray.servlet.WebBoot

class Boot extends WebBoot {

  val ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
  ctx.refresh()

  val services = ctx.getBean(classOf[ActorSystemBean])
  val system = services.system
  override val serviceActor = services.apiRouter 
  

}