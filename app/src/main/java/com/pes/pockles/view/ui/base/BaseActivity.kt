package com.pes.pockles.view.ui.base

import android.content.Context
import android.preference.PreferenceManager
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber
import javax.inject.Inject


/**
 * Base [Activity] that injects the viewModelFactory. It also sets the activity as injectable
 * so Dagger can inject the dependencies automatically
 */
abstract class BaseActivity : DaggerAppCompatActivity() {


    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private var locale: String? = null

    override fun attachBaseContext(newBase: Context) {
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(newBase)
        locale = sharedPreferences.getString("Language", "es")
        super.attachBaseContext(locale?.let { BaseContextWrapper.wrap(newBase, it) })
    }
}