package com.ort.inspira.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _missionTitle = MutableLiveData<String>().apply {
        value = "Misión"
    }
    val missionTitle: LiveData<String> = _missionTitle


    private val _visionTitle = MutableLiveData<String>().apply {
        value = "Visión"
    }
    val visionTitle: LiveData<String> = _visionTitle

    private val _missionDescription = MutableLiveData<String>().apply {
        value = "Nuestra misión consiste en desarrollar e implementar las integraciones de diferentes tecnologías para poder realizar lectura y análisis en tiempo real de " +
                " información de sensores para poder visualizarlos en dashboards y alertar a usuarios finales."
    }
    val missionDescription: LiveData<String> = _missionDescription

    private val _visionDescription = MutableLiveData<String>().apply {
        value = "Nuestra visión es acelerar la interconectividad de los dispositivos IoT con la gente." +
                " Poder ayudar a las personas a utilizar los sensores a su favor."
    }
    val visionDescription: LiveData<String> = _visionDescription
}