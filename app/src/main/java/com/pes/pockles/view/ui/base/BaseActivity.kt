package com.pes.pockles.view.ui.base

import androidx.lifecycle.ViewModelProvider
import com.pes.pockles.di.injector.Injectable
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity(), Injectable {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

}