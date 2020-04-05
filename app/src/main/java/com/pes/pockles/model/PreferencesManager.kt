package com.pes.pockles.model

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(context: Context) {

    private var token : String = ""
        get() {
            return token
        }

    fun setToken(token : String?){
        if (token != null) {
            this.token = token
        }
    }
}