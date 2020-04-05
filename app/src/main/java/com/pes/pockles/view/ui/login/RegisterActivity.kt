package com.pes.pockles.view.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.lifecycle.ViewModelProviders
import com.pes.pockles.R
import com.pes.pockles.util.livedata.EventObserver
import com.pes.pockles.view.ui.MainActivity
import com.pes.pockles.view.ui.base.BaseActivity
import timber.log.Timber

class RegisterActivity : BaseActivity() {

    private val viewModel: RegisterActivityViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(RegisterActivityViewModel::class.java)
    }

    init {
        Timber.d("RegisterActivity")
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_login)

        viewModel
            .registerUser("#ff0044", 860189821, 5)
            .observe(this, EventObserver(::doWhatever))
    }

    private fun doWhatever(b: Boolean) {
        Timber.d("Register user went with value $b")
        if (b) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


}

