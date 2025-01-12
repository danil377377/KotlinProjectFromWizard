package org.example.project.data.network

import org.example.project.data.network.model.Response
import org.example.project.domain.AllShoplistsRepository

class AllShoplistsRepositoryImpl(val dataSource: PurchasesDataSource):AllShoplistsRepository {
    override suspend fun createShoplist(key:String, name: String): Response {
        return dataSource.createShoplist(key,name)
    }

    override suspend fun removeShoplist(id: String): Response {
        return dataSource.removeShoplist(id)
    }
}