package com.pes.pockles.view.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.pes.pockles.R
import com.pes.pockles.databinding.ActivityMainBinding
import com.pes.pockles.view.ui.login.FirebaseActivity
import com.pes.pockles.view.ui.newpock.NewPockActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        var navController = findNavController(R.id.navigationHostFragment)
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)

        binding.fab.setOnClickListener {
            startActivity(Intent(this, NewPockActivity::class.java))
        }

        checkUser()

    }

    fun checkUser() {
        var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            startActivity(Intent(this, FirebaseActivity::class.java))
        }
    }
}
