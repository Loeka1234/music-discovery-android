package com.example.musicdiscovery

import android.app.Application
import com.example.musicdiscovery.data.AppContainer
import com.example.musicdiscovery.data.DefaultAppContainer

class MusicDiscoveryApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}