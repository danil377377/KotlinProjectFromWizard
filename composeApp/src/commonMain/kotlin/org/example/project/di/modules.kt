package org.example.project.data.di


import com.example.mykmpapplicationfromtemplate.domain.PurchasesRepository
import com.example.mykmpapplicationfromtemplate.viewModel.PurchasesViewModel
import org.example.project.data.network.KtorClient
import org.example.project.data.network.PurchasesDataSource
import org.example.project.data.network.PurchasesRepositoryImpl
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    singleOf(::PurchasesRepositoryImpl).bind<PurchasesRepository>()
    singleOf(::KtorClient).bind<PurchasesDataSource>()
    viewModelOf(::PurchasesViewModel)
}