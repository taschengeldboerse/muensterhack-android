package de.muensterhack.marketplace

import de.muensterhack.api.category.Categories

data class TaskViewModel(
        val id: Int?,
        val title: String,
        val description: String?,
        val dueDate: String,
        val duration: Int?,
        val category: Categories?,
        val distance: Int?
)
