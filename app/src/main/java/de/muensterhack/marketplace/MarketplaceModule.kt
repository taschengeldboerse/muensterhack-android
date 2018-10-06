package de.muensterhack.marketplace

import com.google.android.gms.location.LocationServices
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val studentModule = module {

    factory { LocationServices.getFusedLocationProviderClient(androidContext()) }

    factory { FilterPopup(get(), get()) }
}
