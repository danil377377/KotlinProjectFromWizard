package com.example.mykmpapplicationfromtemplate.domain

import org.example.project.data.network.GenerateKeyResponse
import org.example.project.data.network.GetAllShopListsResponse

interface PurchasesRepository {
    suspend fun getAutentificationKey(): GenerateKeyResponse
    suspend fun getAllShopLists(key: String): GetAllShopListsResponse
}