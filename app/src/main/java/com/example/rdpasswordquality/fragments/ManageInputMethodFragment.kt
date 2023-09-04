package com.example.rdpasswordquality.fragments

import android.R
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rdpasswordquality.databinding.FragmentManageInputMethodBinding
import com.example.rdpasswordquality.enterpriserecommended.ManageInputMethodsForDevices
import com.example.rdpasswordquality.receivers.AdminReceiver
import com.example.rdpasswordquality.viewmodels.ManageInputMethodViewModel


class ManageInputMethodFragment : Fragment() {

    private lateinit var binding: FragmentManageInputMethodBinding

    private val viewModel: ManageInputMethodViewModel by lazy {
        ViewModelProvider(this)[ManageInputMethodViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManageInputMethodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val availableInputMethodList: List<String> = viewModel.getAvailableInputMethodList(requireContext()).map { it.packageName }
        val enabledInputMethodList: List<String> = viewModel.getEnabledInputMethodList(requireContext()).map { it.packageName }
        binding.availableInputMethodsListView.adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, availableInputMethodList)
        binding.enabledInputMethodsListView.adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, enabledInputMethodList)

        val componentName = ComponentName(requireContext(), AdminReceiver::class.java)
        val devicePolicyManager = requireContext().getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val manageInputMethodsForDevices = ManageInputMethodsForDevices(requireContext(), devicePolicyManager, componentName)

        binding.empty.setOnClickListener {
            manageInputMethodsForDevices.disableAllNonSystemInputMethods()
        }

        binding.nullValue.setOnClickListener {
            manageInputMethodsForDevices.enableAllNonSystemInputMethods()
        }
    }

}