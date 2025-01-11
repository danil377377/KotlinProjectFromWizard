package org.example.project.data.network

import com.example.mykmpapplicationfromtemplate.domain.PurchasesRepository

class PurchasesRepositoryImpl(val dataSource: PurchasesDataSource): PurchasesRepository {
    override suspend fun getAutentificationKey(): GenerateKeyResponse {
        return dataSource.getAutentificationKey()
    }

    override suspend fun getAllShopLists(key: String): GetAllShopListsResponse {
        return dataSource.getAllShopLists(key)
    }

}