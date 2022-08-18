package com.example.eis.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eis.R
import com.example.eis.ui.models.Mobile
import com.google.android.material.card.MaterialCardView

class MobileAdapter(private val context: Context,
                    private val mobile: ArrayList<Mobile>,
                    private val mobileListener: OnMobileListener
): RecyclerView.Adapter<MobileAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val cvMobile: MaterialCardView = itemView.findViewById(R.id.cv_mobile)
        val tvCategory: TextView = itemView.findViewById(R.id.rowCategory)
        val tvYear: TextView = itemView.findViewById(R.id.rowYear)
        val tvProvince: TextView = itemView.findViewById(R.id.rowProvince)
        val tvStatus: TextView = itemView.findViewById(R.id.rowStatus)
        val tvCount: TextView = itemView.findViewById(R.id.rowCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_mobile, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mobile = mobile[position]
        if(mobile.category == "top-bottom"){
            holder.tvCategory.text = "TOP-BOTTOM"
        }
        else if(mobile.category == "bottom-top"){
            holder.tvCategory.text = "BOTTOM-TOP"
        }
        holder.tvYear.text = mobile.year
        holder.tvProvince.text = mobile.province
        holder.tvStatus.text = mobile.status
        holder.tvCount.text = mobile.count
        holder.cvMobile.setOnClickListener{
            mobileListener.onMobileSelected(mobile)
        }
    }

    override fun getItemCount(): Int {
        return mobile.size
    }
}

interface OnMobileListener{
    fun onMobileSelected(mobile: Mobile)
}