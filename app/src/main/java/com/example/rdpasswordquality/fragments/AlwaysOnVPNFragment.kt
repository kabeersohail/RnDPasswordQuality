package com.example.rdpasswordquality.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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



}