package com.pes.pockles.view.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.pes.pockles.R
import com.pes.pockles.databinding.ActivityMainBinding
import com.pes.pockles.view.ui.newpock.NewPockActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        var navController = findNavController(R.id.navigationHostFragment)
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)

        binding.fab.setOnClickListener {
            startActivity(Intent(this, NewPockActivity::class.java))
        }


    }


}
