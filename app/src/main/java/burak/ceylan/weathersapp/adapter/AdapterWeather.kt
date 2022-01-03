package burak.ceylan.weathersapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import burak.ceylan.weathersapp.R
import burak.ceylan.weathersapp.model.dailyforecast.Temperature
import burak.ceylan.weathersapp.util.StringExtension.formatDate
import burak.ceylan.weathersapp.util.StringExtension.formatHour
import kotlinx.android.synthetic.main.layout_item_weather.view.*

class AdapterWeather(private val list: MutableList<Temperature>): RecyclerView.Adapter<AdapterWeather.WeatherHolder>(){
    class WeatherHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
        val infl = LayoutInflater.from(parent.context)
        return WeatherHolder(infl.inflate(R.layout.layout_item_weather, parent, false))
    }

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        val weather = list[position]
        holder.itemView.txtTemperature.text = "${weather.maximum.value} ${weather.maximum.unit}"
        holder.itemView.txtMin.text = "${weather.minimum.value} ${weather.minimum.unit}"
        holder.itemView.txtDate.text = weather.date?.formatDate()
        holder.itemView.txtTime.text = weather.date?.formatHour()
        holder.itemView.txtFuture.text = weather.future
    }

    override fun getItemCount() = list.size
}

