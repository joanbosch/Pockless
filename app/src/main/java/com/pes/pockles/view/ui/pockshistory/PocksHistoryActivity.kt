package com.pes.pockles.view.ui.pockshistory

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.pes.pockles.R
import com.pes.pockles.data.Resource
import com.pes.pockles.databinding.ActivityPocksHistoryBinding
import com.pes.pockles.model.BindingPockItem
import com.pes.pockles.model.Pock
import com.pes.pockles.view.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_pocks_history.*

class PocksHistoryActivity : AppCompatActivity(){
    private lateinit var binding: ActivityPocksHistoryBinding
    private val viewModel: PocksHistoryViewModel by lazy {
        ViewModelProviders.of(this, ViewModelFactory()).get(PocksHistoryViewModel::class.java)
    }
    // Create the ItemAdapter holding your Items
    private val itemAdapter = ItemAdapter<BindingPockItem>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // Initialize RecyclerView
        // Create the managing FastAdapter, by passing in the itemAdapter
        val fastAdapter = FastAdapter.with(itemAdapter)
        // Set out adapters to the RecyclerView
        var layoutManager = LinearLayoutManager(this)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_pocks_history)
        binding.lifecycleOwner = this
        binding.pocksHistoryViewModel = viewModel

        binding.rvPocksHistory.apply {
            this.layoutManager = layoutManager
            this.adapter = fastAdapter
        }

        //binding.loadingLayout.paddingTop = (binding.loadingLayout.height.toInt()/2)

        initializeObservers()
        viewModel.refreshInformation()
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

        // Add refresh action
        swipePocksHistory.setOnRefreshListener {
            viewModel.refreshInformation()
            binding.swipePocksHistory.isRefreshing = false
        }

    }

    private fun setDataRecyclerView(data: List<Pock>) {
    var pockListBinding: List<BindingPockItem> = data.map { pock ->
        var binding = BindingPockItem()
        binding.pock = pock
        binding
    }
        //Fill and set the items to the ItemAdapter
        itemAdapter.add(pockListBinding)

        //Hide progress bar when pocks history showed
        binding.newPockProgressBar.visibility = View.GONE
    }

}