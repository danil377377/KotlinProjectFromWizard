package org.example.project.domain

import org.example.project.data.network.model.Response

interface ShoppingListRepository {
    suspend fun getShoppingList(listId:String): Response
    suspend fun addToShoppingList(listId: String,name: String, n:String): Response
}