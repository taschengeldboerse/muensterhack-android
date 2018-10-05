package de.muensterhack.marketplace

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat.checkSelfPermission
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.FusedLocationProviderClient
import de.muensterhack.R
import de.muensterhack.api.task.Task
import de.muensterhack.api.task.TaskRepository
import de.muensterhack.ext.formatDate
import kotlinx.android.synthetic.main.fragment_marketplace.*
import org.koin.android.ext.android.inject

class MarketplaceFragment : Fragment() {

    private val fusedLocationClient: FusedLocationProviderClient by inject()

    private val taskRepository: TaskRepository by inject()

    private val taskAdapter = TaskAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_marketplace, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageViewFilter.setOnClickListener {  }

        imageViewAccount.setOnClickListener {  }

        recyclerViewTasks.adapter = taskAdapter

        if (checkSelfPermission(requireContext(), ACCESS_FINE_LOCATION) == PERMISSION_GRANTED) {
            getLastLocation()
        } else {
            requestPermissions(arrayOf(ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_LOCATION && grantResults.isNotEmpty() && grantResults.first() == PERMISSION_GRANTED) {
            getLastLocation()
        } else {
            loadTasks()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { loadTasks(it.latitude, it.longitude) }
    }

    private fun loadTasks(lat: Double? = null, lon: Double? = null) = taskRepository.tasks(lat, lon, displayTasks())

    private fun displayTasks(): (List<Task>) -> Unit = { tasks ->
        taskAdapter.tasks = tasks.map {
            TaskViewModel(it.title, it.description, it.due_date.formatDate(), it.estimated_time_in_minutes)
        }
    }

    companion object {

        private const val REQUEST_CODE_LOCATION = 0
    }
}
