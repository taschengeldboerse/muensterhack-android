package de.muensterhack.api.task

data class Task(
        val title: String,
        val description: String?,
        val due_date: String,
        val estimated_time_in_minutes: Int?,
        val status: Int,
        val category: Int,
        val user: Int?,
        val distance_in_meters: Int? = null,
        val id: Int? = null
)