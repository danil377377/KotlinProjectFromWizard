package org.example.project.data.network

import com.example.mykmpapplicationfromtemplate.domain.WelcomeRepository
import org.example.project.data.network.model.Response

class WelcomeRepositoryImpl(val dataSource: PurchasesDataSource): WelcomeRepository {
    override suspend fun getAutentificationKey(): Response {
        return dataSource.getAutentificationKey()
    }

    override suspend fun getAllShopLists(key: String): Response {
        return dataSource.getAllShopLists(key)
    }

}