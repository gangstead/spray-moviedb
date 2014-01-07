package com.example.models

import spray.json.DefaultJsonProtocol

case class Trailers(val id: Long, val youtube: List[YoutubeTrailer])
case class YoutubeTrailer(val name: String, val size: String, val source: String)


object TrailersJsonProtocol extends DefaultJsonProtocol{
    implicit val youtubeTrailerFormat = jsonFormat3(YoutubeTrailer)
    implicit val trailersFormat = jsonFormat2(Trailers)
}