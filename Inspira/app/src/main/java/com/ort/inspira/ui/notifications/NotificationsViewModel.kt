package com.ort.inspira.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {

    private val _emptyList = MutableLiveData<String>().apply {
        value = "No hay notificaciones para mostrar"
    }
    val emptyList: LiveData<String> = _emptyList
}