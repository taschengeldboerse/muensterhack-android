package de.muensterhack.student

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.checkSelfPermission
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.FusedLocationProviderClient
import de.muensterhack.R
import org.koin.android.ext.android.inject

class StudentFragment : Fragment() {

    private val fusedLocationClient: FusedLocationProviderClient by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_student, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            Snackbar.make(view!!, R.string.grant_location_permission, Snackbar.LENGTH_LONG).show()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            Snackbar.make(view!!, "lat: ${location.latitude}, lon: ${location.longitude}", Snackbar.LENGTH_LONG).show()
        }
    }

    companion object {

        private const val REQUEST_CODE_LOCATION = 0
    }
}
