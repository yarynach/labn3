package com.nulp.labn3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nulp.labn3.R

// Hour class with selection state
data class Hour(val text: String, var selected: Boolean = false)

// Adapter class
class HoursAdapter(val hours: ArrayList<Hour>) : RecyclerView.Adapter<HoursAdapter.HourViewHolder>() {

    var onItemClick: ((Hour) -> Unit)? = null

    class HourViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.tv_hour)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
        val itemHolder = LayoutInflater.from(parent.context).inflate(R.layout.hour_item, parent, false)
        return HourViewHolder(itemHolder)
    }

    override fun getItemCount(): Int = hours.size

    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        val hour = hours[position]
        holder.textView.text = hour.text
        val bg: RelativeLayout = holder.itemView.findViewById(R.id.rl_bg_hour)
        if (hour.selected) {
            bg.setBackgroundResource(R.drawable.hour_picked)
        } else {
            bg.setBackgroundResource(R.drawable.hour)
        }
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(hour) // Pass the clicked hour object
        }
    }
}