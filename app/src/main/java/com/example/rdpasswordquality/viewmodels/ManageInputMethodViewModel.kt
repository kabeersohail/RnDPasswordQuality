package com.example.rdpasswordquality.viewmodels

import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodInfo
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModel

class ManageInputMethodViewModel: ViewModel() {

    fun getPackageNamesOfInputMethods(context: Context): List<String> {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val inputMethodList = inputMethodManager.inputMethodList
        val packageNames = mutableListOf<String>()

        for (inputMethodInfo in inputMethodList) {
            packageNames.add(inputMethodInfo.packageName)
        }

        return packageNames
    }

    fun getEnabledInputMethodList(context: Context): MutableList<InputMethodInfo> {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val ims = imm.enabledInputMethodList
        ims.forEach {
            Log.i("Enterprise Recommended->", it.packageName)
        }
        return ims
    }

    fun getAvailableInputMethodList(context: Context): MutableList<InputMethodInfo> {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val ims = imm.inputMethodList
        ims.forEach {
            Log.i("Enterprise Recommended->", it.packageName)
        }
        return ims
    }

}