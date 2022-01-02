package burak.ceylan.weathersapp.model.dailyforecast


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Temperature(
    @SerializedName("Maximum")
    val maximum: Maximum,
    @SerializedName("Minimum")
    val minimum: Minimum,

    var date: String? = null,
    var future: String? = null
): Parcelable