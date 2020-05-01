package com.pes.pockles.data

import android.content.Context
import android.preference.PreferenceManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FCMTokenManager @Inject constructor(val context: Context) {

    companion object {
        const val FCM_TOKEN = "FCMT"
    }

    var token: String? = null

    init {
       loadToken()
        if (token == null)
            retrieveRegisterToken()
    }

    private fun loadToken(){
        this.token = PreferenceManager.getDefaultSharedPreferences(context)
            .getString(FCM_TOKEN,null)
    }

    private fun retrieveRegisterToken() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token
                saveToken(token)
            })
    }


    private fun saveToken(token: String?) {
        this.token = token
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putString(FCM_TOKEN, token)
        editor.apply()
    }
}