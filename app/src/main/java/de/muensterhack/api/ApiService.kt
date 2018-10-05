package de.muensterhack.api

import de.muensterhack.api.task.Task
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("tasks")
    fun tasks(@Query("lat") lat: Double, @Query("lon") lon: Double): Call<List<Task>>

    @GET("tasks")
    fun tasks(): Call<List<Task>>
}
