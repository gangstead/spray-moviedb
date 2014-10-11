package com.example.services

import com.example.models.PersonSearchQuery
import com.example.models.PersonSearchResults
import scala.concurrent.Future

trait PersonService {

  def getPersonSearchResults(query: PersonSearchQuery): Future[Option[PersonSearchResults]]
  
}