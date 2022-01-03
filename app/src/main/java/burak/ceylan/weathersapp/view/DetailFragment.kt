package burak.ceylan.weathersapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import burak.ceylan.weathersapp.R
import burak.ceylan.weathersapp.adapter.AdapterWeather
import burak.ceylan.weathersapp.model.dailyforecast.Temperature
import burak.ceylan.weathersapp.view.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.animation_view


@AndroidEntryPoint
class DetailFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = arguments?.getParcelableArrayList<Temperature>(HomeFragment.KEY)
        list?.let { list ->
            rcvWeather?.apply {
                adapter = AdapterWeather(list = list)
            }
        }
        var toogIdentifier: String = "night"
        hamster.setOnClickListener {
            if(toogIdentifier == "night") {
                hamster.playAnimation()
                animation_view.setAnimation(R.raw.day_background)
                toogIdentifier = "day"
            }else{
                animation_view.setAnimation(R.raw.night_background)
                toogIdentifier = "night"
                hamster.frame = 1
            }
            animation_view.playAnimation()
        }
    }


}