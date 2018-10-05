package de.muensterhack.api.task

import de.muensterhack.api.ApiService
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

typealias TaskListCallback = (List<Task>) -> Unit

interface TaskRepository {

    fun tasks(latitude: Double? = null, longitude: Double? = null, callback: TaskListCallback)
}

class TaskRepositoryImpl(
        private val apiService: ApiService
) : TaskRepository {

    override fun tasks(latitude: Double?, longitude: Double?, callback: TaskListCallback) {
        doAsync {
            val tasks = apiService.tasks(latitude, longitude).execute().body()!!
            // TODO
            val categories = apiService.categories().execute().body()!!
            uiThread { callback.invoke(tasks) }
        }
    }
}
