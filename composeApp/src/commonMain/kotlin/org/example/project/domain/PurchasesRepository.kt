package com.example.mykmpapplicationfromtemplate.domain

interface PurchasesRepository {
    suspend fun getAutorisationKey(): String
}