package com.example.mykmpapplicationfromtemplate.domain


import org.example.project.data.network.model.Response

interface WelcomeRepository {
    suspend fun getAutentificationKey(): Response
    suspend fun getAllShopLists(key: String): Response
}