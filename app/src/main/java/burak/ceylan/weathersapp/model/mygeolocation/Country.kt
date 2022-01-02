package burak.ceylan.weathersapp.model.mygeolocation


import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("EnglishName")
    val englishName: String,
    @SerializedName("ID")
    val iD: String,
    @SerializedName("LocalizedName")
    val localizedName: String
)