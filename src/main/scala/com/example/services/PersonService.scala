package com.example.services

import com.example.models.PersonSearchQuery
import com.example.models.PersonSearchResults

trait PersonService {

  def getPersonSearchResults(query: PersonSearchQuery): Option[PersonSearchResults]
  
}