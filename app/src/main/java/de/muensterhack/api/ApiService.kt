package de.muensterhack.api

import de.muensterhack.api.bid.Bid
import de.muensterhack.api.task.Task
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("tasks")
    fun tasks(
            @Header("lat") lat: Double? = null,
            @Header("lon") lon: Double? = null,
            @Query("sort") sort: String? = null
    ): Call<List<Task>>

    @POST("tasks")
    fun putTask(@Body task: Task): Call<Void>

    @POST("bids")
    fun putBid(@Body bid: Bid): Call<Void>
}