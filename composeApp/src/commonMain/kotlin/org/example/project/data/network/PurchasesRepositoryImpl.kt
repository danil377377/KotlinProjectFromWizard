package org.example.project.data.network

import com.example.mykmpapplicationfromtemplate.domain.PurchasesRepository

class PurchasesRepositoryImpl(val dataSource: PurchasesDataSource): PurchasesRepository {
    override suspend fun getAutentificationKey(): Response {
        return dataSource.getAutentificationKey()
    }

    override suspend fun getAllShopLists(key: String): Response {
        return dataSource.getAllShopLists(key)
    }

}