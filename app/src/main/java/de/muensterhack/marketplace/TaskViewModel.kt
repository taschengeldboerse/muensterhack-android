package de.muensterhack.marketplace

data class TaskViewModel(
        val title: String,
        val description: String?,
        val dueDate: String,
        val duration: Int?
)
