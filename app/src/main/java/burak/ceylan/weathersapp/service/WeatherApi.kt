package burak.ceylan.weathersapp.service

import burak.ceylan.weathersapp.model.citysearch.CitySearch
import burak.ceylan.weathersapp.model.dailyforecast.DailyForecast
import burak.ceylan.weathersapp.model.mygeolocation.MyGeoLocation
import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.*

interface WeatherApi {

    companion object {
        const val BASE_URL = "https://dataservice.accuweather.com/"
        const val API_KEY = "CujB7TWzRgIqvsa5JPWxGtwdj6kOZ9Dq"
    }

    @GET("locations/v1/cities/geoposition/search")
    suspend fun getKeyForCurrentLocation(@QueryMap param: HashMap<String, String>): Response<MyGeoLocation>

    @GET("locations/v1/cities/search")
    suspend fun getLocationOfCityByName(@QueryMap param: HashMap<String, String>): Response<CitySearch>

    @GET("forecasts/v1/daily/1day/{key}")
    suspend fun getWeatherByKey(@Path("key") id: String, @QueryMap param: HashMap<String, String>): Response<DailyForecast>


}