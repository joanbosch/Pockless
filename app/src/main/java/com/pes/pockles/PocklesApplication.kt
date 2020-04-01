package com.pes.pockles

import android.app.Application
import com.google.firebase.FirebaseApp
import timber.log.Timber

class PocklesApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        FirebaseApp.initializeApp(this)
    }

}