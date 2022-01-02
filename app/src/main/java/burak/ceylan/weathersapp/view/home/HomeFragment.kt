package burak.ceylan.weathersapp.view.home

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import burak.ceylan.weathersapp.R
import burak.ceylan.weathersapp.adapter.AdapterCity
import burak.ceylan.weathersapp.database.entity.CityEntity
import burak.ceylan.weathersapp.view.MainActivity
import burak.ceylan.weathersapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions


@AndroidEntryPoint
class HomeFragment : Fragment(), AdapterCity.CityListener {
    private val weatherViewModel :WeatherViewModel by activityViewModels()
    private var  mLocationManager: LocationManager? = null
    private var cityList = mutableListOf<CityEntity>()
    private var cityName = ""
    private var adapterCity: AdapterCity? = null
    private var isGetWeatherHere = true
    private var isClickChooseCity = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private val mLocationListener: LocationListener =
        LocationListener { p0 ->
            isGetWeatherHere = true
            val latitude = p0.latitude
            val longitude = p0.longitude
            weatherViewModel.getWeatherHere(latitude, longitude)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRcv()
        activity?.let { it ->
            mLocationManager = activity?.getSystemService(LOCATION_SERVICE) as LocationManager
        }
        requestLocationPermission()
        button?.setOnClickListener {
            isGetWeatherHere = false
            isClickChooseCity = false
            if (cityList.size == 5) {
                weatherViewModel.deleteCity(cityList[4])
            }
            cityName = editText?.text?.toString() ?: ""
            if (cityName.isNotEmpty()) {
                weatherViewModel.getWeatherOfCityByName(cityName = cityName)
            }
            rcvCity?.visibility = View.GONE
        }

        editText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (rcvCity.visibility == View.GONE) {
                    rcvCity.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        weatherViewModel.weatherLiveData.observe(viewLifecycleOwner, Observer{
            val weather = it.first
            val key = it.second
            if (!isClickChooseCity) {
                weatherViewModel.insertCity(CityEntity(name = cityName, key = key))
            }
        })

        weatherViewModel.getAllCity().observe(viewLifecycleOwner, { list ->
            cityList.clear()
            cityList.addAll(list.toMutableList().reversed())
            adapterCity?.setList(cityList)
        })

    }

    private fun setupRcv() {
        adapterCity = AdapterCity(this)
        rcvCity?.apply {
            adapter = adapterCity
        }
    }

    @AfterPermissionGranted(123)
    private fun requestLocationPermission() {
        val perms =
            arrayOf(
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

    override fun onCityClicked(city: CityEntity) {
        isClickChooseCity = true
        isGetWeatherHere = false
        rcvCity?.visibility = View.GONE
        weatherViewModel.getWeatherCityByKey(key = city.key)
    }

}