package burak.ceylan.weathersapp.model.citysearch


import com.google.gson.annotations.SerializedName

data class SupplementalAdminArea(
    @SerializedName("EnglishName")
    val englishName: String,
    @SerializedName("Level")
    val level: Int,
    @SerializedName("LocalizedName")
    val localizedName: String
)