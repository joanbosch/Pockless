package com.pes.pockles.model

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