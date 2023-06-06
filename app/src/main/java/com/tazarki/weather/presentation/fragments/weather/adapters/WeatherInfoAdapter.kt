package com.tazarki.weather.presentation.fragments.weather.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tazarki.domain.weather.dto.WeatherInfo
import com.tazarki.weather.R
import com.tazarki.weather.presentation.utils.Utilities
import javax.inject.Inject

class WeatherInfoAdapter @Inject constructor() :
    RecyclerView.Adapter<WeatherInfoAdapter.ViewHolder>(){

    private val items = ArrayList<WeatherInfo>()

    fun setData(list: ArrayList<WeatherInfo>){
        items.clear()
        items.addAll(list)
        notifyItemInserted(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val row = items[position]
        val temp = String.format("%.2f", Utilities.kelvinToCelsius(row.temperature))

        holder.tvCity.text = row.city
        holder.tvTemp.apply {
            text = this.context.getString(R.string.celsius, temp)
        }

        Glide.with(holder.ivCloud.context)
            .asDrawable()
            .placeholder(R.drawable.ic_launcher_foreground)
            .load(row.cloudIcon)
            .into(holder.ivCloud)

        changeRowColor(holder, position)

    }

    // Change the background and text color of every other item
    private fun changeRowColor(holder: ViewHolder, position: Int){
        if (position % 2 == 0){
            holder.view.setBackgroundColor(ContextCompat.getColor(holder.view.context, R.color.colorPrimary))
            holder.tvCity.setTextColor(ContextCompat.getColor(holder.tvCity.context, R.color.white))
            holder.tvTemp.setTextColor(ContextCompat.getColor(holder.tvCity.context, R.color.white))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvCity: TextView = itemView.findViewById(R.id.tv_ville)
        var tvTemp: TextView = itemView.findViewById(R.id.tv_temp)
        var ivCloud: ImageView = itemView.findViewById(R.id.iv_nuage)
        var view = itemView
    }

}