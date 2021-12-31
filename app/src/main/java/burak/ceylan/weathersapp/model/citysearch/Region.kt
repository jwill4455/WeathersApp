package burak.ceylan.weathersapp.model.citysearch


import com.google.gson.annotations.SerializedName

data class Region(
    @SerializedName("EnglishName")
    val englishName: String,
    @SerializedName("ID")
    val iD: String,
    @SerializedName("LocalizedName")
    val localizedName: String
)