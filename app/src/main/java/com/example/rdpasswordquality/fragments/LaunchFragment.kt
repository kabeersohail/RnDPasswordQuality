package com.example.rdpasswordquality.fragments

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.UserManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodInfo
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.rdpasswordquality.R
import com.example.rdpasswordquality.databinding.FragmentLaunchBinding
import com.example.rdpasswordquality.enterpriserecommended.ManageInputMethodsForDevices
import com.example.rdpasswordquality.receivers.AdminReceiver
import com.example.rdpasswordquality.viewmodels.LaunchViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LaunchFragment : Fragment() {

    lateinit var binding: FragmentLaunchBinding

    private val viewModel: LaunchViewModel by lazy {
         ViewModelProvider(this)[LaunchViewModel::class.java]
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
        binding.dpmRestriction.setAdapter(adapter)
        observe()
    }

    private fun observe() {
        val componentName = ComponentName(requireContext(), AdminReceiver::class.java)
        val devicePolicyManager = requireContext().getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager

        val manageInputMethodsForDevices = ManageInputMethodsForDevices(requireContext(), devicePolicyManager, componentName)
        var availableInputMethods: MutableList<InputMethodInfo> = mutableListOf()
        viewModel.selectedRestriction.observe(requireActivity()) {

            when(it) {
                "Always-On VPN" -> {

                }
                "GET DEVICE MAC ADDRESS" -> {
                    manageInputMethodsForDevices.getDeviceMacAddress()
                }
                "MANAGE INPUT METHODS FOR DEVICES" -> {
                    findNavController().navigate(R.id.action_launchFragment_to_manageInputMethod)
                }
                "SET CUSTOM LOCK SCREEN MESSAGE" -> devicePolicyManager.setCustomLockScreenMessage(componentName)
                "DISABLE FACTORY RESET" -> {
                    devicePolicyManager.addUserRestriction(componentName, UserManager.DISALLOW_FACTORY_RESET)
                    Log.i("Enterprise Recommended->", "${devicePolicyManager.isFactoryResetDisabled(componentName)}")
                }
                "ENABLE FACTORY RESET" -> {
                    devicePolicyManager.clearUserRestriction(componentName, UserManager.DISALLOW_FACTORY_RESET)
                    Log.i("Enterprise Recommended->", "${devicePolicyManager.isFactoryResetDisabled(componentName)}")
                }
                "SET BRAVE AS DEFAULT BROWSER" -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        devicePolicyManager.setFireFoxAsPersistentPreferredActivity()
                    }
                }
            }
        }
    }

    private fun DevicePolicyManager.isFactoryResetDisabled(componentName: ComponentName): Boolean = getUserRestrictions(componentName).getBoolean(UserManager.DISALLOW_FACTORY_RESET)

    private fun DevicePolicyManager.setFireFoxAsPersistentPreferredActivity() {

        val componentName = ComponentName(requireContext(), AdminReceiver::class.java)

        val firefoxComponentName = ComponentName("org.mozilla.firefox", "org.mozilla.firefox.App")
        val intentFilter = IntentFilter(Intent.ACTION_VIEW)
        intentFilter.addCategory(Intent.CATEGORY_BROWSABLE)
        addPersistentPreferredActivity(componentName, intentFilter, firefoxComponentName)
    }

    private fun DevicePolicyManager.setCustomLockScreenMessage(componentName: ComponentName) {
        setDeviceOwnerLockScreenInfo(componentName, "This is my custom lock screen message")
    }
}