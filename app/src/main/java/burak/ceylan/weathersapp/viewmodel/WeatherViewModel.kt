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
    val weatherLiveData = MutableLiveData<Pair<DailyForecast, String>>()
    val weatherHereLiveData = MutableLiveData<Pair<DailyForecast, String>>()
    val nameCityLiveData = MutableLiveData<String>()

    fun getWeatherHere(latitude: Double, longitude: Double) {
        try {
            coroutineContext.launch {
                val response = weatherRepository.getKeyForCurrentLocation(latitude, longitude)
                response?.let {
                    nameCityLiveData.value = it.localizedName
                    val key = it.key
                    val weather = weatherRepository.getWeatherByKey(key = key)
                    weather?.let { weather ->
                        weatherHereLiveData.value = weather to key
                        Log.e("TAG", "weather $weather")
                    }
                }
            }

        } catch (ex: Exception) {
        }
    }

    fun getWeatherCityByKey(key: String) {
        try {
            coroutineContext.launch {
                val weather = weatherRepository.getWeatherByKey(key = key)
                weather?.let { weather ->
                    weatherLiveData.value = weather to key
                    Log.e("TAG", "Weather $weather")
                }
            }
        } catch (ex: Exception) {

        }
    }

    fun deleteCity(cityEntity: CityEntity) {
        try {
            coroutineContext.launch {
                weatherRepository.deleteCity(cityEntity)
            }
        } catch (ex: Exception) {
            Log.e("tag", "delete failure ${ex.toString()}")
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
                            weatherLiveData.value = weather to key
                        }
                    }
                }
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
            Log.e("tag", "insert failure ${ex.toString()}")
        }
    }

    fun getAllCity() = weatherRepository.getAllCityRecent()

}