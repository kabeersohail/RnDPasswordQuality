package com.example.rdpasswordquality.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rdpasswordquality.R

class AlwaysOnVPNAdapter(private val vpnApps: List<String>) :
    RecyclerView.Adapter<AlwaysOnVPNAdapter.AlwaysOnVPNViewHolder>() {

    inner class AlwaysOnVPNViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.vpn_app_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlwaysOnVPNViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_vpn_app_design, parent, false)
        return AlwaysOnVPNViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlwaysOnVPNViewHolder, position: Int) {
        val item = vpnApps[position]
        holder.textView.text = item
    }

    override fun getItemCount(): Int {
        return vpnApps.size
    }
}
