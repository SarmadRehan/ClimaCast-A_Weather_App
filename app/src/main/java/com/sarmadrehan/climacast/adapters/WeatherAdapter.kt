package com.sarmadrehan.climacast.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sarmadrehan.climacast.R
import com.sarmadrehan.climacast.models.CurrentWeather

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    private val differCallBack = object : DiffUtil.ItemCallback<CurrentWeather>() {
        override fun areItemsTheSame(oldItem: CurrentWeather, newItem: CurrentWeather): Boolean {
            return oldItem.current == newItem.current
        }

        override fun areContentsTheSame(oldItem: CurrentWeather, newItem: CurrentWeather): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.renderview_weather_time,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val currentWeather = differ.currentList[position]

        holder.itemView.apply {
//            Glide.with(this).load()
        }
        setOnItemClickListener {
            onItemClickListener?.let { it(currentWeather) }
        }
    }

    private var onItemClickListener:((CurrentWeather) -> Unit)? =null

    private fun setOnItemClickListener(listener:(CurrentWeather) -> Unit){
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}