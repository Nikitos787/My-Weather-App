package com.example.weatherappmy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherappmy.R
import com.example.weatherappmy.databinding.ListItemBinding
import com.example.weatherappmy.model.WeatherHour
import com.squareup.picasso.Picasso

class HoursAdapter() : ListAdapter<WeatherHour, HoursAdapter.Holder>(Comparator()) {

    class Holder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ListItemBinding.bind(view)
        var itemTemp: WeatherHour? = null

        fun bind(item: WeatherHour) = with(binding) {
            val temperatureFormatted = "${item.temp_c.toFloat().toInt()} ℃"
            itemTemp = item
            textForDateItem.text = item.time
            textConditionItem. text = item.condition.text
            textTempItem.text = temperatureFormatted
            Picasso.get().load("https:" + item.condition.icon).into(imageConditionOfItem)
        }
    }

    class Comparator: DiffUtil.ItemCallback<WeatherHour>() {
        override fun areItemsTheSame(oldItem: WeatherHour, newItem: WeatherHour): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WeatherHour, newItem: WeatherHour): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}