package com.example.models

abstract class SearchQuery {
  
  val query: String
  val page: Int
  
  def queryParamsAmpPrefix: String = {
    s"&query=${this.query}&page=${this.page}"
  }
  
}