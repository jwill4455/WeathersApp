package burak.ceylan.weathersapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import burak.ceylan.weathersapp.R
import burak.ceylan.weathersapp.database.entity.CityEntity
import kotlinx.android.synthetic.main.layout_item_city.view.*

class AdapterCity(private val listener: CityListener): RecyclerView.Adapter<AdapterCity.CityHolder>(){
    private var list = mutableListOf<CityEntity>()

    fun setList(cityList: MutableList<CityEntity>) {
        this.list = cityList
        notifyDataSetChanged()
    }

    class CityHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val infl = LayoutInflater.from(parent.context)
        return CityHolder(infl.inflate(R.layout.layout_item_city, parent, false))
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        val city = list[position]
        holder.itemView.txtItemCity?.text = city.name
        holder.itemView.root?.setOnClickListener {
            listener.onCityClicked(city)
        }
    }

    override fun getItemCount() = list.size

    interface CityListener {
        fun onCityClicked(city: CityEntity)
    }
}