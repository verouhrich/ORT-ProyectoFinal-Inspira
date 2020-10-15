package com.ort.inspira.login

import com.google.android.gms.tasks.Task

interface LoginAdapter {
    fun <T> iniciarSesion(user: String, password: String): Task<T>
}