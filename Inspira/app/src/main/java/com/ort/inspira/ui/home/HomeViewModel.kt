package com.ort.inspira.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _missionTitle = MutableLiveData<String>().apply {
        value = "Bienvenidos a Inspira"
    }
    val missionTitle: LiveData<String> = _missionTitle

    private val _missionDescription = MutableLiveData<String>().apply {
        value = "Inspira es un sistema de seguridad para hoteles el cual tiene como finalidad alertar" +
                " en tiempo real a los usuarios de cambios en el ambiente e indicarles cómo deben actuar ante ellos. "
    }
    val missionDescription: LiveData<String> = _missionDescription

}