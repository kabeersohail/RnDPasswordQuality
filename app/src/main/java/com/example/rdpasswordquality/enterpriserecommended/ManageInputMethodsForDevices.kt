package com.example.rdpasswordquality.enterpriserecommended

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodInfo
import android.view.inputmethod.InputMethodManager


class ManageInputMethodsForDevices(private val context : Context, private val devicePolicyManager: DevicePolicyManager, private val componentName: ComponentName) {

    fun getDeviceMacAddress() {
        val macAddress = devicePolicyManager.getWifiMacAddress(componentName)
        Log.i("Enterprise Recommended->", "MAC ADDRESS -> $macAddress")
    }

    /**
     * During testing this method returned true
     */
    fun setPermittedNonSystemInputMethods() {
        val isSuccess = devicePolicyManager.setPermittedInputMethods(componentName, getEnabledInputMethodList().map { it.packageName })
        Log.i("Enterprise Recommended->", "input restriction -> $isSuccess")
    }

    /**
     * Calling with a null value for the list disables the restriction so that all input methods can be used.
     *
     * During testing this method returned true
     */
    fun enableAllNonSystemInputMethods() {
        val isSuccess = devicePolicyManager.setPermittedInputMethods(componentName, null)
        Log.i("Enterprise Recommended->", "input restriction -> $isSuccess")
    }

    /**
     * Calling with an empty list disables all but the system's own input methods.
     *
     * During testing this method returned false
     */
    fun disableAllNonSystemInputMethods() {
        val isSuccess = devicePolicyManager.setPermittedInputMethods(componentName, emptyList())
        Log.i("Enterprise Recommended->", "input restriction -> $isSuccess")
    }

    @Suppress("unused")
    fun getPackageNamesOfInputMethods(): List<String> {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val inputMethodList = inputMethodManager.inputMethodList
        val packageNames = mutableListOf<String>()

        for (inputMethodInfo in inputMethodList) {
            packageNames.add(inputMethodInfo.packageName)
        }

        return packageNames
    }

    private fun getEnabledInputMethodList(): MutableList<InputMethodInfo> {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val ims = imm.enabledInputMethodList
        ims.forEach {
            Log.i("Enterprise Recommended->", it.packageName)
        }
        return ims
    }

    fun getInputMethodList(): MutableList<InputMethodInfo> {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val ims = imm.inputMethodList
        ims.forEach {
            Log.i("Enterprise Recommended->", it.packageName)
        }
        return ims
    }

}