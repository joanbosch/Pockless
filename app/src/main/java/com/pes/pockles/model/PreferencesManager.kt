package com.pes.pockles.model

import android.content.SharedPreferences
import com.firebase.ui.auth.IdpResponse

object PreferencesManager {

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