package org.example.project.data.di


import org.example.project.data.Preferences.PlatformHttpClient
import org.example.project.data.Preferences.PreferencesImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule = module {
    singleOf(::PreferencesImpl)
singleOf(::PlatformHttpClient)
}