package com.ort.inspira.ui.notifications

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

data class Notification(var title: String, var body: String, var date: Timestamp) {
    constructor() : this("Default Title","Default Body", Timestamp(0, 0 ))
}