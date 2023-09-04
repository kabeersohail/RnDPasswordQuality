package com.example.rdpasswordquality.fragments

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

        binding.vpnAppsRecyclerView.adapter = AlwaysOnVPNAdapter(listOf("Sohail", "Salman", "Nirgin"))
    }



}