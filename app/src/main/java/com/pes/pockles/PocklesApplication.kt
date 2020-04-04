package com.pes.pockles

import android.app.Application
import com.pes.pockles.di.injector.initInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import com.google.firebase.FirebaseApp
import timber.log.Timber
import javax.inject.Inject

class PocklesApplication : Application(), HasAndroidInjector {

    @Inject lateinit var androidInjector : DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        FirebaseApp.initializeApp(this)

        initInjector(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}