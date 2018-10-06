package de.muensterhack.api.task

import de.muensterhack.api.ApiService
import de.muensterhack.api.bid.Bid
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

private const val SORT_VALUE = "{\"\$uri\":true}"

typealias TaskListCallback = (List<Task>) -> Unit

interface TaskRepository {

    fun tasks(latitude: Double? = null, longitude: Double? = null, callback: TaskListCallback)

    fun putTask(task: Task, callback: () -> Unit)

    fun confirmTask(id: Int?, user: Int?, callback: () -> Unit)
}

class TaskRepositoryImpl(
        private val apiService: ApiService
) : TaskRepository {

    override fun tasks(latitude: Double?, longitude: Double?, callback: TaskListCallback) {
        doAsync {
            val tasks = apiService.tasks(latitude, longitude, SORT_VALUE).execute().body()!!
            uiThread { callback.invoke(tasks) }
        }
    }

    override fun putTask(task: Task, callback: () -> Unit) {
        doAsync {
            val execute = apiService.putTask(task).execute()
            uiThread { callback.invoke() }
        }
    }

    override fun confirmTask(id: Int?, user: Int?, callback: () -> Unit) {
        if (id != null && user != null) {
            doAsync {
                val execute = apiService.putBid(Bid(id, user)).execute()
                uiThread { callback.invoke() }
            }
        }
    }
}
