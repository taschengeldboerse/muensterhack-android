package de.muensterhack.api

import de.muensterhack.api.task.Task
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    @GET("tasks")
    fun tasks(@Header("lat") lat: Double? = null, @Header("lon") lon: Double? = null): Call<List<Task>>
}
