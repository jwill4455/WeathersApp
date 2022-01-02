package burak.ceylan.weathersapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import burak.ceylan.weathersapp.database.entity.CityEntity
import burak.ceylan.weathersapp.model.citysearch.CitySearch
import burak.ceylan.weathersapp.model.dailyforecast.DailyForecast
import burak.ceylan.weathersapp.model.mygeolocation.MyGeoLocation
import burak.ceylan.weathersapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {
    private val viewModelJob = Job()
    private val coroutineContext = CoroutineScope(Dispatchers.Main + viewModelJob)
    val weatherLiveData = MutableLiveData<DailyForecast>()


    fun getWeatherHere(latitude: Double, longitude: Double) {
        try {
            coroutineContext.launch {
                val response = weatherRepository.getKeyForCurrentLocation(latitude, longitude)
                response?.let {
                    val key = it.key
                    val weather = weatherRepository.getWeatherByKey(key = key)
                    weather?.let { weather ->
                        weatherLiveData.value = weather
                    }
                }
                Log.e("TAG", "response $response")
            }

        } catch (ex: Exception) {
        }
    }

    fun getWeatherOfCityByName(cityName: String) {
        try {
            coroutineContext.launch {
                val response = weatherRepository.getLocationOfCityByName(cityName)
                response?.let {
                    it.firstOrNull()?.let { city ->
                        val key = city.key
                        val weather = weatherRepository.getWeatherByKey(key = key)
                        weather?.let { weather ->
                            weatherLiveData.value = weather
                        }
                    }
                }
                Log.e("TAG", "response $response")
            }

        } catch (ex: Exception) {
        }
    }

    fun insertCity(cityEntity: CityEntity) {
        try {
            coroutineContext.launch {
               weatherRepository.insertCity(cityEntity)
            }
        } catch (ex: Exception) {
        }
    }

    fun getAllCity() = weatherRepository.getAllCityRecent()
}