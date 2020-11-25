package com.ort.inspira.ui.notifications

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

data class Notification(var title: String, var body: String, var color: String, var date: Timestamp) {
    constructor() : this("Default Title","Default Body", "#616161", Timestamp(0, 0 ))
}