package de.muensterhack.marketplace

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.view.Gravity.END
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.widget.PopupWindowCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import de.muensterhack.R
import de.muensterhack.api.category.Categories.Companion.getById
import de.muensterhack.api.task.Task
import de.muensterhack.api.task.TaskRepository
import de.muensterhack.ext.formatDate
import de.muensterhack.preferences.Preferences
import kotlinx.android.synthetic.main.fragment_marketplace.*
import org.koin.android.ext.android.inject

class MarketplaceFragment : Fragment(), FilterListener, TaskConfirmListener {

    private val fusedLocationClient: FusedLocationProviderClient by inject()

    private val taskRepository: TaskRepository by inject()
    private val filterPopup: FilterPopup by inject()
    private val preferences: Preferences by inject()

    private val taskAdapter = TaskAdapter(this)

    private var taskViewModels: List<TaskViewModel> = emptyList()
    private var filteredCategories: List<Int> = emptyList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_marketplace, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout.setOnRefreshListener { loadTasks() }
        filterPopup.filterListener = this
        filteredCategories = preferences.getFilteredCategories()

        imageViewFilter.setOnClickListener {
            val offset = resources.getDimension(R.dimen.toolbar_menu_offset_y).toInt()
            PopupWindowCompat.showAsDropDown(filterPopup, imageViewFilter, 0, offset, END)
        }

//        imageViewProfile.setOnClickListener { findNavController().navigate(R.id.profileFragment) }

        buttonAdd.setOnClickListener { findNavController().navigate(R.id.chooseCategoryFragment) }

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

    override fun filterChanged(filteredCategories: List<Int>) {
        this.filteredCategories = filteredCategories
        taskAdapter.tasks = taskViewModels.filter { filteredCategories.contains(it.category?.id) }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener {
            if (it == null) {
                loadTasks()
            } else {
                loadTasks(it.latitude, it.longitude)
            }
        }
    }

    private fun loadTasks(lat: Double? = null, lon: Double? = null) = taskRepository.tasks(lat, lon, displayTasks())

    private fun displayTasks(): (List<Task>) -> Unit = { tasks ->
        swipeRefreshLayout.isRefreshing = false

        taskViewModels = tasks.map {
            TaskViewModel(it.id, it.title, it.description, it.due_date.formatDate(), it.estimated_time_in_minutes,
                    getById(it.category), it.distance_in_meters)
        }

        taskAdapter.tasks = taskViewModels.filter { filteredCategories.contains(it.category?.id) }.sortedBy { it.distance }
    }

    override fun taskConfirmed(id: Int?) {
        taskRepository.confirmTask(id, 1) {

        }
    }

    companion object {

        private const val REQUEST_CODE_LOCATION = 0
    }
}
