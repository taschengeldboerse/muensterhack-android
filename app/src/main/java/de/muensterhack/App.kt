package de.muensterhack

import android.app.Application
import de.muensterhack.api.apiModule
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(apiModule))
    }
}