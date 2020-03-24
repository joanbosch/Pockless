package com.pes.pockles.view.ui.pockshistory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.pes.pockles.R
import com.pes.pockles.databinding.ActivityPocksHistoryBinding
import com.pes.pockles.view.viewmodel.ViewModelFactory

class PocksHistoryActivity : AppCompatActivity(){
    private lateinit var binding: ActivityPocksHistoryBinding
    private val viewModel: PocksHistoryViewModel by lazy {
        ViewModelProviders.of(this, ViewModelFactory()).get(PocksHistoryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.)
        binding.lifecycleOwner = this
        binding.pocksHistoryViewModel = viewModel
    }
}