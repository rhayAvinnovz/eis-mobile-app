package com.example.eis.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eis.R
import com.example.eis.ui.models.Area
import com.example.eis.ui.models.Stationary
import com.google.android.material.card.MaterialCardView

class AreaAdapter(private val context: Context,
                        private val area: ArrayList<Area>,
                        private val areaListener: OnAreaListener
): RecyclerView.Adapter<AreaAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val cvArea: MaterialCardView = itemView.findViewById(R.id.cv_area)
        val tvSource: TextView = itemView.findViewById(R.id.rowSource)
        val tvYear: TextView = itemView.findViewById(R.id.rowYear)
        val tvCity: TextView = itemView.findViewById(R.id.rowCity)
        val tvStatus: TextView = itemView.findViewById(R.id.rowStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_area, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val area = area[position]
        holder.tvSource.text = area.source
        holder.tvYear.text = area.year
        holder.tvCity.text = area.city
        holder.tvStatus.text = area.status
        holder.cvArea.setOnClickListener{
            areaListener.onAreaSelected(area)
        }
    }

    override fun getItemCount(): Int {
        return area.size
    }
}

interface OnAreaListener{
    fun onAreaSelected(area: Area)
}