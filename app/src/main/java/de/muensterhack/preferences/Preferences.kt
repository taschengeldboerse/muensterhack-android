package de.muensterhack.preferences

import android.content.Context
import android.content.Context.MODE_PRIVATE
import de.muensterhack.api.category.Categories

private const val PREFERENCES_NAME = "taschengeldboerse_prefs"
private const val PREF_FILTERED_CATEGORIES = "filtered_categories"
private const val DELIMITER = ","
private val DEFAULT_FILTERED_CATEGORIES = Categories.values().map { it.id }.joinToString(DELIMITER)

interface Preferences {

    fun setFilteredCategories(categories: List<Int>)

    fun getFilteredCategories(): List<Int>
}

class PreferencesImpl(
        context: Context
) : Preferences {

    private val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)

    override fun setFilteredCategories(categories: List<Int>) {
        sharedPreferences
                .edit()
                .putString(PREF_FILTERED_CATEGORIES, categories.joinToString(DELIMITER))
                .apply()
    }

    override fun getFilteredCategories(): List<Int> {
        val categories = sharedPreferences
                .getString(PREF_FILTERED_CATEGORIES, DEFAULT_FILTERED_CATEGORIES)!!

        return if (categories.isEmpty()) emptyList() else categories.split(DELIMITER).map(String::toInt)
    }
}
