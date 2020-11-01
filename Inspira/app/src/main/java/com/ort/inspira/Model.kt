package com.ort.inspira

import com.google.type.DateTime
import java.time.LocalDateTime
import java.util.*

data class Notification(var title: String, var body: String, var date: LocalDateTime)

object Supplier {
    val Notifications = listOf<Notification>(
        Notification("Alerta Roja", "Evacuar al punto mas cercano, mira el protocolo de evacuacion", LocalDateTime.now()),
        Notification("Alerta Naranja", "Atento a las instrucciones del staff, mira el protocolo de evacuacion", LocalDateTime.now()),
        Notification("Alerta Amarillo", "Evacuar al punto mas cercano, mira el protocolo de evacuacion", LocalDateTime.now())

    )
}