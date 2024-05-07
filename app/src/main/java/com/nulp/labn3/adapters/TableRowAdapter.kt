package com.nulp.labn3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nulp.labn3.db.Appointments
import com.nulp.labn3.R

class TableRowAdapter(private var appointmentsList:List<Appointments>):RecyclerView.Adapter<TableRowAdapter.ViewHolder>() {

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val tvFullName: TextView = itemView.findViewById(R.id.tv_fullName)
        val tvAge:TextView = itemView.findViewById(R.id.tv_Age)
        val tvType:TextView = itemView.findViewById(R.id.tv_Type)
        val tvDate:TextView = itemView.findViewById(R.id.tv_Date)
        val tvTime:TextView = itemView.findViewById(R.id.tv_Time)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.table_row_layout,viewGroup,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return appointmentsList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.tvFullName.text=appointmentsList[i].fullName
        viewHolder.tvAge.text= appointmentsList[i].age.toString()
        viewHolder.tvDate.text=appointmentsList[i].date.toString()
        viewHolder.tvTime.text=appointmentsList[i].time
        viewHolder.tvType.text=appointmentsList[i].appointmentType
    }
}