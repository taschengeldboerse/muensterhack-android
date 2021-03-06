package de.muensterhack.api

import de.muensterhack.api.task.TaskRepository
import de.muensterhack.api.task.TaskRepositoryImpl
import okhttp3.OkHttpClient
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_ENDPOINT = "https://api.taschengeldboerse.io"

val apiModule = module {

    single<ApiService> { get<Retrofit>().create(ApiService::class.java) }

    single<Retrofit> {
        Retrofit.Builder()
                .client(get())
                .baseUrl(API_ENDPOINT)
                .addConverterFactory(get<GsonConverterFactory>())
                .build()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
                .build()
    }

    single<GsonConverterFactory> { GsonConverterFactory.create() }

    factory<TaskRepository> { TaskRepositoryImpl(get()) }
}
