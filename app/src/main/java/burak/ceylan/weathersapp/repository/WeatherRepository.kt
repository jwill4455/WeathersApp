package burak.ceylan.weathersapp.repository

import androidx.lifecycle.ViewModel
import burak.ceylan.weathersapp.database.CityDao
import burak.ceylan.weathersapp.database.CityDatabase
import burak.ceylan.weathersapp.database.entity.CityEntity
import burak.ceylan.weathersapp.model.citysearch.CitySearch
import burak.ceylan.weathersapp.model.dailyforecast.DailyForecast
import burak.ceylan.weathersapp.model.mygeolocation.MyGeoLocation
import burak.ceylan.weathersapp.service.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherApi: WeatherApi,
    private val cityDatabase: CityDatabase
) : ViewModel() {
    private val cityDao = cityDatabase.getCityDao()

    fun getAllCityRecent() = cityDao.getAllCity()

    suspend fun insertCity(city: CityEntity) {
        cityDao.insertCity(city)
    }

    suspend fun getKeyForCurrentLocation(latitude: Double, longitude: Double): MyGeoLocation? {
        val hashMap = hashMapOf<String, String>()
        hashMap["apikey"] = WeatherApi.API_KEY
        hashMap["q"] = "${latitude},${longitude}"
        return weatherApi.getKeyForCurrentLocation(param = hashMap).body()
    }

    suspend fun getWeatherByKey(key: String): DailyForecast? {
        return weatherApi.getWeatherByKey(id = key).body()
    }

    suspend fun getLocationOfCityByName(cityName: String): CitySearch? {
        val hashMap = hashMapOf<String, String>()
        hashMap["apikey"] = WeatherApi.API_KEY
        hashMap["q"] = cityName
        return weatherApi.getLocationOfCityByName(param = hashMap).body()
    }
}