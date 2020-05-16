package com.pes.pockles.view.ui.settings

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.pes.pockles.R
import com.pes.pockles.databinding.ActivitySettingsBinding
import com.pes.pockles.view.ui.base.BaseActivity
import com.pes.pockles.view.ui.settings.SettingsViewModel

class SettingsActivity : BaseActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val viewModel: SettingsViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(SettingsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        //Add back button to toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)


        //Initialize observers
        initializeObservers()

        //Define Actions
        initializeListeners()

    }

    private fun initializeListeners() {

    }


    private fun initializeObservers() {

    }

}