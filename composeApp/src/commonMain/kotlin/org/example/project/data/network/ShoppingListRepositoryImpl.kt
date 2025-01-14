package org.example.project.data.network

import org.example.project.data.network.model.Response
import org.example.project.data.network.model.ShoppingListResponse
import org.example.project.domain.ShoppingListRepository

class ShoppingListRepositoryImpl(val dataSource: PurchasesDataSource):ShoppingListRepository {
    override suspend fun getShoppingList(listId: String): Response {
        return dataSource.getShoppingList(listId)
    }
    override suspend fun addToShoppingList(listId: String,name: String, n:String): Response{
       return dataSource.addToShoppingList(listId,name,n)
    }
}