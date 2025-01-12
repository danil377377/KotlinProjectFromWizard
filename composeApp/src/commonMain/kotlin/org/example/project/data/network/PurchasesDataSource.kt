package org.example.project.data.network

import org.example.project.data.network.model.Response

interface PurchasesDataSource {
    suspend fun getAutentificationKey(): Response
    suspend fun getAllShopLists(key:String): Response
    suspend fun createShoplist(key:String, name:String):Response
    suspend fun removeShoplist(id:String):Response
}