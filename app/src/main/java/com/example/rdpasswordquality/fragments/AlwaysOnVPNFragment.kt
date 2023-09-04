package com.example.rdpasswordquality.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rdpasswordquality.adapters.AlwaysOnVPNAdapter
import com.example.rdpasswordquality.databinding.FragmentAlwaysOnVpnBinding

class AlwaysOnVPNFragment : Fragment() {

    lateinit var binding: FragmentAlwaysOnVpnBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlwaysOnVpnBinding.inflate(inflater, null, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext()) // Use requireContext() to get the context
        binding.vpnAppsRecyclerView.layoutManager = layoutManager

        binding.vpnAppsRecyclerView.adapter = AlwaysOnVPNAdapter(fetchTheListOfVPNAppsInstalledOnTheDevice(requireContext()))
    }

    private fun fetchTheListOfVPNAppsInstalledOnTheDevice(context: Context): List<String> {
        val vpnAppsList = mutableListOf<String>()

        // You can use PackageManager to query installed apps
        val packageManager = context.packageManager
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        val resolveInfoList = packageManager.queryIntentActivities(intent, 0)

        for (resolveInfo in resolveInfoList) {
            val packageName = resolveInfo.activityInfo.packageName
            // Check if the app is a VPN app based on some criteria
            if (isVPNApp(packageName, context)) {
                val appName = resolveInfo.loadLabel(packageManager).toString()
                vpnAppsList.add(appName)
            }
        }

        return vpnAppsList
    }

    // Helper function to determine if an app is a VPN app based on some criteria
    private fun isVPNApp(packageName: String, context: Context): Boolean {
        // Implement your criteria here
        // For example, you could check if the app's name or package name contains "VPN" or similar keywords
        // You might need to do additional checks depending on your specific requirements
        return packageName.contains("vpn", ignoreCase = true)
    }




}