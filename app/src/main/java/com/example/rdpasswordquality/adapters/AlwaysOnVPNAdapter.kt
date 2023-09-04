package com.example.rdpasswordquality.adapters

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.rdpasswordquality.R
import com.example.rdpasswordquality.extensions.setSelectedPackage
import com.example.rdpasswordquality.receivers.AdminReceiver

class AlwaysOnVPNAdapter(
    private val context: Context,
    private val vpnApps: List<String>,
    private val devicePolicyManager: DevicePolicyManager
) :
    RecyclerView.Adapter<AlwaysOnVPNAdapter.AlwaysOnVPNViewHolder>() {

    private val componentName = ComponentName(context, AdminReceiver::class.java)

    inner class AlwaysOnVPNViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.vpn_app_name)
        val vpnLayout: ConstraintLayout = itemView.findViewById(R.id.vpn_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlwaysOnVPNViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_vpn_app_design, parent, false)
        return AlwaysOnVPNViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlwaysOnVPNViewHolder, position: Int) {
        val item = vpnApps[position]
        holder.textView.text = item

        holder.vpnLayout.setOnClickListener {
            context.setSelectedPackage(vpnApps[position], componentName, devicePolicyManager)
        }
    }

    override fun getItemCount(): Int {
        return vpnApps.size
    }

}
