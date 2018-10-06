package de.muensterhack.preferences

import android.content.Context
import android.content.Context.MODE_PRIVATE
import de.muensterhack.api.category.Categories
import org.joda.time.DateTime

private const val PREFERENCES_NAME = "taschengeldboerse_prefs"
private const val PREF_FILTERED_CATEGORIES = "filtered_categories"
private const val PREF_WHAT_TO_DO = "what_to_do"
private const val PREF_DESCRIPTION = "more"
private const val PREF_DURATION = "duration"
private const val PREF_DUE_DATE = "due_date"
private const val DELIMITER = ","
private val DEFAULT_FILTERED_CATEGORIES = Categories.values().map { it.id }.joinToString(DELIMITER)

interface Preferences {

    fun setFilteredCategories(categories: List<Int>)

    fun getFilteredCategories(): List<Int>

    fun setWhatToDo(whatToDo: String)

    fun getWhatToDo(): String

    fun setDescription(description: String)

    fun getDescription(): String

    fun setDuration(duration: Int)

    fun getDuration(): Int

    fun getDueDate(): DateTime

    fun setDueDate(dueDate: DateTime)
}

class PreferencesImpl(
        context: Context
) : Preferences {

    private val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)

    override fun setFilteredCategories(categories: List<Int>) = sharedPreferences
            .edit()
            .putString(PREF_FILTERED_CATEGORIES, categories.joinToString(DELIMITER))
            .apply()

    override fun getFilteredCategories(): List<Int> {
        val categories = sharedPreferences
                .getString(PREF_FILTERED_CATEGORIES, DEFAULT_FILTERED_CATEGORIES)!!

        return if (categories.isEmpty()) emptyList() else categories.split(DELIMITER).map(String::toInt)
    }

    override fun setWhatToDo(whatToDo: String) = sharedPreferences
            .edit()
            .putString(PREF_WHAT_TO_DO, whatToDo)
            .apply()

    override fun getWhatToDo() = sharedPreferences.getString(PREF_WHAT_TO_DO, "")!!

    override fun setDescription(description: String) = sharedPreferences
            .edit()
            .putString(PREF_DESCRIPTION, description)
            .apply()

    override fun getDescription() = sharedPreferences.getString(PREF_DESCRIPTION, "")!!

    override fun setDuration(duration: Int) = sharedPreferences
            .edit()
            .putInt(PREF_DURATION, duration)
            .apply()

    override fun getDuration() = sharedPreferences.getInt(PREF_DURATION, 0)

    override fun getDueDate() = DateTime(sharedPreferences.getLong(PREF_DUE_DATE, DateTime.now().plusDays(1).millis))

    override fun setDueDate(dueDate: DateTime) {
        sharedPreferences
                .edit()
                .putLong(PREF_DUE_DATE, dueDate.millis)
                .apply()
    }
}
