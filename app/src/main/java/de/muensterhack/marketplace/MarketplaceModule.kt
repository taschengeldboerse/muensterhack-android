package de.muensterhack.marketplace

import com.google.android.gms.location.LocationServices
import de.muensterhack.api.task.TaskRepository
import de.muensterhack.api.task.TaskRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val studentModule = module {

    factory { LocationServices.getFusedLocationProviderClient(androidContext()) }

    factory<TaskRepository> { TaskRepositoryImpl(get()) }
}
