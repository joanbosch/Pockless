package com.pes.pockles.data

import android.content.Context
import android.preference.PreferenceManager
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.internal.InternalTokenResult
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages the token state inside the application, updating it if necessary and saving it
 * into the internal storage of the application, concretely to the default [SharedPreferences] of
 * the application.
 */
@Singleton
class TokenManager @Inject constructor(val context: Context) {

    companion object {
        const val TOKEN_KEY = "TOKEN"
    }

    var token: String? = null

    init {
        //https://firebase.google.com/docs/reference/android/com/google/firebase/auth/FirebaseAuth.IdTokenListener#public-method-summary
        loadToken()
        FirebaseAuth.getInstance()
            .addIdTokenListener { i: InternalTokenResult ->
                run {
                    this.token?.let { t ->
                        {
                            this.saveToken(t)
                        }
                    }
                }
            }
    }

    fun refreshToken(): String? {
        val user = FirebaseAuth.getInstance().currentUser
        return if (user != null) {
            val token = Tasks.await(user.getIdToken(true))
            if (token != null && token.token != null) {
                saveToken(token.token!!)
            }
            token.token
        } else null
    }

    private fun loadToken() {
        this.token = PreferenceManager.getDefaultSharedPreferences(context)
            .getString(TOKEN_KEY, null)
    }

    private fun saveToken(token: String) {
        this.token = token
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putString(TOKEN_KEY, token)
        editor.apply()
    }
}