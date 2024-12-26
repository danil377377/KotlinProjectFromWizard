package org.example.project.data.network

interface PurchasesDataSource {
    suspend fun getAutentificationKey(): String
}