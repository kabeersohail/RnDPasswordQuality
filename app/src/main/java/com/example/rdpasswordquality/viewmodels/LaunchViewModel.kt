package com.example.rdpasswordquality.viewmodels

import android.util.Log
import android.widget.AutoCompleteTextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class LaunchViewModel : ViewModel() {

    private val _selectedRestriction: MutableLiveData<String> = MutableLiveData()
    val selectedRestriction: LiveData<String> = _selectedRestriction

    fun applyPasswordQuality(quality: AutoCompleteTextView) {
        Log.d("LaunchViewModel",quality.text.toString())
        _selectedRestriction.postValue(quality.text.toString())
    }

}