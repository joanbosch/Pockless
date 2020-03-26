package com.pes.pockles.view.ui.pockshistory

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pes.pockles.R
import com.pes.pockles.databinding.ActivityPocksHistoryBinding
import com.pes.pockles.model.Pock
import com.pes.pockles.view.viewmodel.ViewModelFactory

class PocksHistoryActivity : AppCompatActivity(){
    private lateinit var binding: ActivityPocksHistoryBinding
    private val viewModel: PocksHistoryViewModel by lazy {
        ViewModelProviders.of(this, ViewModelFactory()).get(PocksHistoryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pocks_history)
        binding.lifecycleOwner = this
        binding.pocksHistoryViewModel = viewModel

        initializeObservers()
        initializeRecyclerView()
    }

    private fun initializeObservers() {
        viewModel.pocksHistory.observe(
            this,
            Observer { value: List<Pock>? ->
                value?.let {
                    initializeRecyclerView()
                }
            }
        )
    }

    private fun initializeRecyclerView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}