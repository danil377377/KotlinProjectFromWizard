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
import org.example.project.data.network.Response

class KtorClient : PurchasesDataSource {
    private val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }
    private val BASE_URL = "https://cyberprot.ru/shopping/v2"

    override suspend fun getAutentificationKey(): Response {
        return try {
            withTimeout(3.seconds) {
                val response = httpClient.get("$BASE_URL/CreateTestKey?")
                response.body<GenerateKeyResponse>().apply { resultCode = 200 }
            }
        } catch (ex: kotlinx.coroutines.TimeoutCancellationException) {
            Response().apply { resultCode = -3 }
        }
        catch (ex: UnresolvedAddressException){
            Response().apply { resultCode = -1 }
        }
    }

    override suspend fun getAllShopLists(key: String): Response {
        return try {
            withTimeout(3.seconds) {
                val response = httpClient.get("$BASE_URL/GetAllMyShopLists?key=$key")
                response.body<GetAllShopListsResponse>().apply { resultCode = 200 }
            }
        } catch (ex: kotlinx.coroutines.TimeoutCancellationException) {
            Response().apply { resultCode = -3 }
        }
        catch (ex: UnresolvedAddressException){
            Response().apply { resultCode = -1 }
        }

    }
}