package com.example.rdpasswordquality.fragments

import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rdpasswordquality.R
import com.example.rdpasswordquality.databinding.FragmentLaunchBinding
import com.example.rdpasswordquality.receivers.AdminReceiver
import com.example.rdpasswordquality.utils.passwordQualityConverter
import com.example.rdpasswordquality.viewmodels.LaunchViewModel


class LaunchFragment : Fragment() {

    lateinit var binding: FragmentLaunchBinding

    private val viewModel: LaunchViewModel by lazy {
         ViewModelProvider(this)[LaunchViewModel::class.java]
    }

    private val onsetNewPassword = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK || it.resultCode == Activity.RESULT_FIRST_USER){
            showToastAndLog("onSetNewPassword success")
        } else if(it.resultCode == Activity.RESULT_CANCELED){
            showToastAndLog("onsetNewPassword cancelled")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLaunchBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val passwordQualities = resources.getStringArray(R.array.password_quality)
        val adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,passwordQualities)
        binding.selectedPasswordQuality.setAdapter(adapter)
        observe()
    }

    private fun observe(){
        viewModel.selectedQuality.observe(viewLifecycleOwner){
            val componentName = ComponentName(requireContext(), AdminReceiver::class.java)
            val devicePolicyManager = requireContext().getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
            devicePolicyManager.setPasswordQuality(componentName, passwordQualityConverter(it))

            setUpNewPassword()

        }
    }

    private fun setUpNewPassword() {
        val setNewPasswordIntent = Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD)
        onsetNewPassword.launch(setNewPasswordIntent)
    }

    private fun showToastAndLog(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        Log.d("LaunchFragment", message)
    }

}