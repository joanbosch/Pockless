package com.pes.pockles.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.internal.InternalTokenResult
import javax.inject.Singleton

@Singleton
class TokenManager {

    var token: String? = null

    init {
        //https://firebase.google.com/docs/reference/android/com/google/firebase/auth/FirebaseAuth.IdTokenListener#public-method-summary
        FirebaseAuth.getInstance()
            .addIdTokenListener { i: InternalTokenResult -> this.token = i.token }
    }
}