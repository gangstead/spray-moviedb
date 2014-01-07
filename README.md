spray-moviedb
=============

An exploration in using the Spray routing and HTTP client libraries, along with Spring and themoviedb.org API

This project was created primarily as an excuse to play around with the Spray libraries.  It's essentially a set of proxy services for a very small subset of the API provided by themoviedb.org.  The application uses Spray's routing DSL to define a set of RESTful services on the front end, and the Http client library to integrate with themoviedb.org on the backend.

Spring is used for no particularly good reason other than to show that it can be incorporated into a Spray/Akka application.  The approach was pretty much lifted from [Christopher Hunt's repository:](https://github.com/huntc/akka-spring/blob/ba6704703efa45c9c638c3ac3b4b07f022d061ec/src/main/scala/org/typesafe/Akkaspring.scala#L48)


After cloning, the first thing you'll want to do to get any use out of this project is to [register an account and request an API key at themoviedb.org](http://docs.themoviedb.apiary.io/).

Once you have an API key, open GlobalConstants.scala and replace *putyourmoviedbapikeyhere* with your API key.


The project builds and deploys a Spray web application to an embedded Jetty server using the [xsbt-web-plugin](https://github.com/JamesEarlDouglas/xsbt-web-plugin).

To start the application, cd to the "spray-moviedb" directory, start sbt, and then execute `container:start`

