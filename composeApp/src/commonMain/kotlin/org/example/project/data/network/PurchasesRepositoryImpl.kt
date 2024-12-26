package org.example.project.data.network

import com.example.mykmpapplicationfromtemplate.domain.PurchasesRepository

class PurchasesRepositoryImpl(val dataSource: PurchasesDataSource): PurchasesRepository {
    override suspend fun getAutorisationKey(): String {
        return dataSource.getAutentificationKey()
    }

}