package com.ort.inspira

import java.util.*

data class Notification(var title: String, var body: String, var date: Date)

object Supplier {
    val Notifications = listOf<Notification>(
        Notification("Alerta Roja", "Evacuar al punto mas cercano, mira el protocolo de evacuacion", Date()),
        Notification("Alerta Naranja", "Atento a las instrucciones del staff, mira el protocolo de evacuacion", Date()),
        Notification("Alerta Amarillo", "Evacuar al punto mas cercano, mira el protocolo de evacuacion", Date()),
        Notification("Alerta Roja", "Evacuar al punto mas cercano, mira el protocolo de evacuacion", Date()),
        Notification("Alerta Naranja", "Atento a las instrucciones del staff, mira el protocolo de evacuacion", Date()),
        Notification("Alerta Amarillo", "Evacuar al punto mas cercano, mira el protocolo de evacuacion", Date()),
        Notification("Alerta Roja", "Evacuar al punto mas cercano, mira el protocolo de evacuacion", Date()),
        Notification("Alerta Naranja", "Atento a las instrucciones del staff, mira el protocolo de evacuacion", Date()),
        Notification("Alerta Amarillo", "Evacuar al punto mas cercano, mira el protocolo de evacuacion", Date())
    )
}