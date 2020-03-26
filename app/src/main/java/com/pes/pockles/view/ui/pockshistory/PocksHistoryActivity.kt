package com.pes.pockles.view.ui.pockshistory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.pes.pockles.R
import com.pes.pockles.data.Resource
import com.pes.pockles.databinding.ActivityPocksHistoryBinding
import com.pes.pockles.model.BindingPockItem
import com.pes.pockles.model.Pock
import com.pes.pockles.view.viewmodel.ViewModelFactory

class PocksHistoryActivity : AppCompatActivity(){
    private lateinit var binding: ActivityPocksHistoryBinding
    private val viewModel: PocksHistoryViewModel by lazy {
        ViewModelProviders.of(this, ViewModelFactory()).get(PocksHistoryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Initialize RecyclerView
        // Create the ItemAdapter holding your Items
        val itemAdapter = ItemAdapter<BindingPockItem>()
        // Create the managing FastAdapter, by passing in the itemAdapter
        val fastAdapter = FastAdapter.with(itemAdapter)
        // Set out adapters to the RecyclerView
        binding.rvPocksHistory.adapter



        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pocks_history)
        binding.lifecycleOwner = this
        binding.pocksHistoryViewModel = viewModel

        initializeObservers()
    }

    private fun initializeRecyclerView() {

    }

    private fun initializeObservers() {
        viewModel.pocksHistory.observe(
            this,
            Observer { value: Resource<List<Pock>>? ->
                value?.let {
                    when(value) {
                        is Resource.Success<List<Pock>> -> setDataRecyclerView(value.data)
                    }
                }
            }
        )
    }

    private fun setDataRecyclerView(data: List<Pock>) {
    var a: List<Int> = data.map { pock ->
        var s:Int = 5
        s
    }

    }

}