package com.pes.pockles.view.ui.pockshistory

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.pes.pockles.R
import com.pes.pockles.data.Resource
import com.pes.pockles.databinding.ActivityPocksHistoryBinding
import com.pes.pockles.model.Pock
import com.pes.pockles.view.ui.base.BaseActivity
import com.pes.pockles.view.ui.pockshistory.item.BindingPockItem

class PocksHistoryActivity : BaseActivity() {

    private lateinit var binding: ActivityPocksHistoryBinding
    private val viewModel: PocksHistoryViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(PocksHistoryViewModel::class.java)
    }

    // Create the ItemAdapter holding your Items
    private val itemAdapter = ItemAdapter<BindingPockItem>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_pocks_history)
        binding.lifecycleOwner = this
        binding.pocksHistoryViewModel = viewModel

        // Add back-button to toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        binding.rvPocksHistory.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = FastAdapter.with(itemAdapter)
        }

        initializeObservers()
        viewModel.refreshInformation()
    }

    private fun initializeObservers() {
        viewModel.pocksHistory.observe(
            this,
            Observer { value: Resource<List<Pock>>? ->
                value?.let {
                    when (value) {
                        is Resource.Success<List<Pock>> -> setDataRecyclerView(value.data)
                        is Resource.Error -> binding.swipePocksHistory.isRefreshing = false
                    }
                }
            }
        )

        // Action for back-button on toolbar
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        // Add refresh action
        binding.swipePocksHistory.setOnRefreshListener {
            viewModel.refreshInformation()
        }

    }

    private fun setDataRecyclerView(data: List<Pock>) {
        binding.swipePocksHistory.isRefreshing = false

        val pockListBinding: List<BindingPockItem> = data.map { pock ->
            val binding =
                BindingPockItem()
            binding.pock = pock
            binding
        }
        //Fill and set the items to the ItemAdapter
        itemAdapter.setNewList(pockListBinding)

        //Hide progress bar when pocks history showed
        binding.newPockProgressBar.visibility = View.GONE
    }

}