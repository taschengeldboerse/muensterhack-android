package de.muensterhack

import android.app.Application
import de.muensterhack.api.apiModule
import de.muensterhack.marketplace.studentModule
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin(this, listOf(
                apiModule,
                studentModule
        ))
    }
}