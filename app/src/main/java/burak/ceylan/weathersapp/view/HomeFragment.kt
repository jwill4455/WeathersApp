package burak.ceylan.weathersapp.view

import android.Manifest
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import burak.ceylan.weathersapp.R
import burak.ceylan.weathersapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val weatherViewModel :WeatherViewModel by activityViewModels()
    private var  mLocationManager: LocationManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private val mLocationListener: LocationListener =
        LocationListener { p0 -> Log.e("tag", "location ${p0.latitude} ${p0.longitude}") }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { it ->
            mLocationManager = activity?.getSystemService(LOCATION_SERVICE) as LocationManager


        }

        button?.setOnClickListener {
            //test
           // weatherViewModel.getWeatherHere(41.015137, 28.979530)\
            requestLocationPermission()
        }
    }

    @AfterPermissionGranted(123)
    private fun requestLocationPermission() {
        val perms =
            arrayOf<String>(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        if (EasyPermissions.hasPermissions(requireContext()!!, *perms)) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            mLocationManager?.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 10L,
                10F, mLocationListener
            )
        } else {
            EasyPermissions.requestPermissions(
                this, "We need permissions to get weather",
                MainActivity.REQUEST_CODE_LOCATION, *perms
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

}