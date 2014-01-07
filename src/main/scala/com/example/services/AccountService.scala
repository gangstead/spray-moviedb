package com.example.services

import com.example.models.Account
import com.example.models.FavoritesAdd
import com.example.models.FavoritesQuery
import com.example.models.FavoritesQueryResults

trait AccountService {
  
  def getAccount(sessionId: String): Account
  def getAccountFavorites(favoritesQuery: FavoritesQuery, sessionId: String): Option[FavoritesQueryResults]
  def addAccountFavorite(favoritesAdd: FavoritesAdd, sessionId: String): Unit

}