spray-moviedb
=============

An exploration in using the [Spray](http://spray.io/) routing and HTTP client libraries, along with [Spring](http://spring.io/) and [themoviedb.org](http://www.themoviedb.org/) API

This project was created primarily as an excuse to play around with the Spray libraries.  It's essentially a set of proxy services for a very small subset of the API provided by themoviedb.org.  The application uses Spray's routing DSL to define a set of RESTful services on the front end, and the Http client library to integrate with themoviedb.org on the backend.

Spring is used for no particularly good reason other than to show that it can be incorporated into a Spray/Akka application.  The approach was pretty much lifted from [Christopher Hunt's repository:](https://github.com/huntc/akka-spring/blob/ba6704703efa45c9c638c3ac3b4b07f022d061ec/src/main/scala/org/typesafe/Akkaspring.scala#L48)


Running the app
---------------

After cloning, the first thing you'll want to do before building and running the project is to [register an account and request an API key at themoviedb.org](http://docs.themoviedb.apiary.io/).

Once you obtain an API key, open GlobalConstants.scala and replace *putyourmoviedbapikeyhere* with your API key.


The project builds and deploys to an embedded Jetty server using the [xsbt-web-plugin](https://github.com/JamesEarlDouglas/xsbt-web-plugin).

To start the application, cd to the *spray-moviedb* directory, start `sbt`, and then execute `>container:start`


Request catalog
---------------

Sample requests are listed below:

####Movies / People

Search the movie catalog (query required, page optional):
`http://localhost:8080/movies?query=fight&page=1`

Get a movie by id:
`http://localhost:8080/movies/100`

Get movie cast by movie id:
`http://localhost:8080/movies/100/cast`

Get movie trailers by movie id:
`http://localhost:8080/movies/100/trailers`

Search for people (query required, page optional):
`http://localhost:8080/people?query=Spacey&page=1`

####Account / Favorites

The requests in this section require that you log into your moviedb account and grant permissions to the locally running app to access your user data.

1.  Open a browser and go to http://localhost:8080/login
2.  Login
3.  Select "Allow"


Get account information:
`http://localhost:8080/account`

Get favorite movies list:
`http://localhost:8080/account/favorites`

Add a movie (POST) to your favorite movies list:
```http://localhost:8080/account/favorites

{
 "movie_id": 103,
 "favorite": true
}```



