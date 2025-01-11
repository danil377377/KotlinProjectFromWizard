package com.example.mykmpapplicationfromtemplate.domain


import org.example.project.data.network.Response

interface PurchasesRepository {
    suspend fun getAutentificationKey(): Response
    suspend fun getAllShopLists(key: String): Response
}