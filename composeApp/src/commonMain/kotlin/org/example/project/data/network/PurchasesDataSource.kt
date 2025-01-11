package org.example.project.data.network

interface PurchasesDataSource {
    suspend fun getAutentificationKey(): GenerateKeyResponse
    suspend fun getAllShopLists(key:String): GetAllShopListsResponse
}