package com.zero.mvvm.root

import android.app.Application
import com.zero.mvvm.data.network.ApiClient

class App : Application() {
    companion object {
        lateinit var service: ApiClient
    }

    override fun onCreate() {
        super.onCreate()
        service = ApiClient()
    }
}