package org.example.project

import android.app.Application
import org.example.project.data.di.initKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin() {

        }
    }
}