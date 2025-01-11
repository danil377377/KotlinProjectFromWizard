package com.example.mykmpapplicationfromtemplate.domain

import org.example.project.data.network.GenerateKeyResponse
import org.example.project.data.network.GetAllShopListsResponse
import org.example.project.data.network.Response

interface PurchasesRepository {
    suspend fun getAutentificationKey(): Response
    suspend fun getAllShopLists(key: String): GetAllShopListsResponse
}