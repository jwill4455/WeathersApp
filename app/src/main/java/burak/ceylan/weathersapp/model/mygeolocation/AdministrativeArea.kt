package burak.ceylan.weathersapp.model.mygeolocation


import com.google.gson.annotations.SerializedName

data class AdministrativeArea(
    @SerializedName("CountryID")
    val countryID: String,
    @SerializedName("EnglishName")
    val englishName: String,
    @SerializedName("EnglishType")
    val englishType: String,
    @SerializedName("ID")
    val iD: String,
    @SerializedName("Level")
    val level: Int,
    @SerializedName("LocalizedName")
    val localizedName: String,
    @SerializedName("LocalizedType")
    val localizedType: String
)