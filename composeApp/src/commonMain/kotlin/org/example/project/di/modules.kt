package org.example.project.data.di


import com.example.mykmpapplicationfromtemplate.domain.WelcomeRepository
import com.example.mykmpapplicationfromtemplate.viewModel.WelcomeViewModel
import org.example.project.data.network.AllShoplistsRepositoryImpl
import org.example.project.data.network.KtorClient
import org.example.project.data.network.PurchasesDataSource
import org.example.project.data.network.WelcomeRepositoryImpl
import org.example.project.domain.AllShoplistsRepository
import org.example.project.viewModel.AllShopListsViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    singleOf(::WelcomeRepositoryImpl).bind<WelcomeRepository>()
    singleOf(::AllShoplistsRepositoryImpl).bind<AllShoplistsRepository>()
    singleOf(::KtorClient).bind<PurchasesDataSource>()
    viewModelOf(::WelcomeViewModel)
    viewModelOf(::AllShopListsViewModel)
}