package org.example.project.domain

import org.example.project.data.network.model.Response

interface AllShoplistsRepository {
    suspend fun createShoplist(key:String, name:String): Response
    suspend fun removeShoplist(id:String):Response
}