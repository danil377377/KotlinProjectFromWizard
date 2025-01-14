package org.example.project.data.network

import org.example.project.data.network.model.Response

interface PurchasesDataSource {
    suspend fun getAutentificationKey(): Response
    suspend fun getAllShopLists(key:String): Response
    suspend fun createShoplist(key:String, name:String):Response
    suspend fun removeShoplist(id:String):Response
    suspend fun getShoppingList(listId: String): Response
    suspend fun addToShoppingList(listId: String,name: String, n:String): Response
    suspend fun removeFromList(listId: String,itemId:String): Response
}