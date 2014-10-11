package com.example.services

import com.example.models.Account
import com.example.models.FavoritesAdd
import com.example.models.FavoritesQuery
import com.example.models.FavoritesQueryResults
import scala.concurrent.Future

trait AccountService {
  
  def getAccount(sessionId: String): Future[Account]
  def getAccountFavorites(favoritesQuery: FavoritesQuery, sessionId: String): Future[Option[FavoritesQueryResults]]
  def addAccountFavorite(favoritesAdd: FavoritesAdd, sessionId: String): Future[Unit]

}