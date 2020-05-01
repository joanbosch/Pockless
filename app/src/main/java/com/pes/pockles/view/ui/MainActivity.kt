package com.pes.pockles.view.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.pes.pockles.R
import com.pes.pockles.data.repository.UserRepository
import com.pes.pockles.databinding.ActivityMainBinding
import com.pes.pockles.view.ui.base.BaseActivity
import com.pes.pockles.view.ui.newpock.NewPockActivity
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var userRepository: UserRepository

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navController = findNavController(R.id.navigationHostFragment)
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)

        binding.fab.setOnClickListener {
            startActivity(Intent(this, NewPockActivity::class.java))
        }
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
        retrieveRegisterToken()
    }

    private fun retrieveRegisterToken() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token
                Timber.i(token)

            })
    }

}
