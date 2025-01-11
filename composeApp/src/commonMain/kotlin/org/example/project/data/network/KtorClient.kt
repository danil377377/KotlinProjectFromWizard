package org.example.project.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration.Companion.seconds


class KtorClient : PurchasesDataSource {
    private val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }
    private val BASE_URL = "https://cyberprot.ru/shopping/v2"

    override suspend fun getAutentificationKey(): GenerateKeyResponse {
        return try {
            withTimeout(3.seconds) {
                val response = httpClient.get("$BASE_URL/CreateTestKey?")
                GenerateKeyResponse(response.body(), null)
            }
        } catch (ex: kotlinx.coroutines.TimeoutCancellationException) {
            GenerateKeyResponse(null,  "ERROR:Request timeout after 3 seconds")
        }
        catch (ex: UnresolvedAddressException){
            GenerateKeyResponse(null,  "ERROR:No internet connection")
        }
    }

    override suspend fun getAllShopLists(key: String): GetAllShopListsResponse {
        return httpClient.get("$BASE_URL/GetAllMyShopLists?key=$key").body()
    }
}