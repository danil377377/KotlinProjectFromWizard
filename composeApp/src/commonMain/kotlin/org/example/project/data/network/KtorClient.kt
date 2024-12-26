package org.example.project.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json


class KtorClient : PurchasesDataSource {
    private val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }
    private val BASE_URL = "https://cyberprot.ru/shopping/v2"

    override suspend fun getAutentificationKey(): String{
        return httpClient.get("$BASE_URL/CreateTestKey?"){
        }.body()
    }
}