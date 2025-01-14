package org.example.project.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import org.example.project.data.network.model.AddToShoplistResponse
import org.example.project.data.network.model.CreateShoplistsResponse
import org.example.project.data.network.model.CrossItemResponse
import org.example.project.data.network.model.GenerateKeyResponse
import org.example.project.data.network.model.GetAllShopListsResponse
import org.example.project.data.network.model.RemoveFromListResponse
import org.example.project.data.network.model.RemoveShoplistResponse
import org.example.project.data.network.model.Response
import org.example.project.data.network.model.ShoppingListResponse

class KtorClient : PurchasesDataSource {
    private val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }
    private val BASE_URL = "https://cyberprot.ru/shopping/v2"

    override suspend fun getAutentificationKey(): Response {
        return safeApiCall({
            val response = httpClient.get("$BASE_URL/CreateTestKey?")
            response.body<GenerateKeyResponse>().apply { resultCode = 200 }
        })
    }

    override suspend fun getAllShopLists(key: String): Response {
        return safeApiCall({
            val response = httpClient.get("$BASE_URL/GetAllMyShopLists?key=$key")
            response.body<GetAllShopListsResponse>().apply { resultCode = 200 }
        })
    }

    override suspend fun createShoplist(key: String, name: String): Response {
        return safeApiCall({
            val response = httpClient.get(
                "$BASE_URL/CreateShoppingList?key=$key&name=${
                    name.replace(
                        Regex("\\s"), "%20"
                    )
                }"
            )
            response.body<CreateShoplistsResponse>().apply { resultCode = 200 }
        })
    }

    override suspend fun removeShoplist(id: String): Response {
        return safeApiCall({
            val response = httpClient.get("$BASE_URL/RemoveShoppingList?list_id=$id")
            response.body<RemoveShoplistResponse>().apply { resultCode = 200 }
        })
    }

    override suspend fun getShoppingList(listId: String): Response {
        return safeApiCall({
            val response = httpClient.get("$BASE_URL/GetShoppingList?list_id=$listId")
            response.body<ShoppingListResponse>().apply { resultCode = 200 }
        })
    }
    override suspend fun addToShoppingList(listId: String,name: String, n:String): Response {
        return safeApiCall({
            val response = httpClient.get("$BASE_URL/AddToShoppingList?id=$listId&value=$name&n=$n")
            response.body<AddToShoplistResponse>().apply { resultCode = 200 }
        })
    }
    override suspend fun removeFromList(listId: String,itemId:String): Response {
        return safeApiCall({
            val response = httpClient.get("$BASE_URL/RemoveFromList?list_id=$listId&item_id=$itemId")
            response.body<RemoveFromListResponse>().apply { resultCode = 200 }
        })
    }
    override suspend fun crossItem(itemId:String): Response {
        return safeApiCall({
            val response = httpClient.get("$BASE_URL/CrossItOff?id=$itemId")
            response.body<CrossItemResponse>().apply { resultCode = 200 }
        })
    }
}