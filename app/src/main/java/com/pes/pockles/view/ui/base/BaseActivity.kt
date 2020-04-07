package com.pes.pockles.view.ui.base

import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * Base [Activity] that injects the viewModelFactory. It also sets the activity as injectable
 * so Dagger can inject the dependencies automatically
 */
abstract class BaseActivity : DaggerAppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

}