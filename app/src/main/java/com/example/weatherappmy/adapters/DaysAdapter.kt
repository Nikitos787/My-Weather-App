package com.example.weatherappmy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherappmy.R
import com.example.weatherappmy.databinding.ListItemBinding
import com.example.weatherappmy.model.WeatherForecastDay
import com.squareup.picasso.Picasso

class DaysAdapter() : ListAdapter<WeatherForecastDay, DaysAdapter.Holder>(Comparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class Comparator() : DiffUtil.ItemCallback<WeatherForecastDay>() {
        override fun areItemsTheSame(
            oldItem: WeatherForecastDay,
            newItem: WeatherForecastDay
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: WeatherForecastDay,
            newItem: WeatherForecastDay
        ): Boolean {
            return oldItem == newItem
        }
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemBinding.bind(view)

        fun bind(item: WeatherForecastDay) = with(binding) {
            val minMax = "${item.day.maxtemp_c.toFloat().toInt()} ℃ / ${
                item.day.mintemp_c.toFloat().toInt()} ℃"
            textForDateItem.text = item.date
            textConditionItem.text = item.day.condition.text
            textTempItem.text = minMax
            Picasso.get().load("https:" + item.day.condition.icon).into(imageConditionOfItem)
        }
    }
}
