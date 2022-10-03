package com.example.rdpasswordquality.viewmodels

import android.util.Log
import android.widget.AutoCompleteTextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class LaunchViewModel : ViewModel() {

    private val _selectedQuality: MutableLiveData<String> = MutableLiveData()
    val selectedQuality: LiveData<String> = _selectedQuality

    fun applyPasswordQuality(quality: AutoCompleteTextView){
        Log.d("SOHAIL",quality.text.toString())
        _selectedQuality.postValue(quality.text.toString())
    }

    /**
     * 1. WeBox demo
     * 2. Youtube WeBox tutorial
     * 3. Check if WeSupport demo is possible
     */

}