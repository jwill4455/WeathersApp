package burak.ceylan.weathersapp.model.dailyforecast


import com.google.gson.annotations.SerializedName

data class DailyForecastX(
    @SerializedName("Date")
    val date: String,
    @SerializedName("Day")
    val day: Day,
    @SerializedName("EpochDate")
    val epochDate: Int,
    @SerializedName("Link")
    val link: String,
    @SerializedName("MobileLink")
    val mobileLink: String,
    @SerializedName("Night")
    val night: Night,
    @SerializedName("Sources")
    val sources: List<String>,
    @SerializedName("Temperature")
    val temperature: Temperature
)