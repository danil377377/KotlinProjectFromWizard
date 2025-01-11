package org.example.project.data.network

interface PurchasesDataSource {
    suspend fun getAutentificationKey(): Response
    suspend fun getAllShopLists(key:String): Response
}