package com.pes.pockles.view.ui.map

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.pes.pockles.R
import com.pes.pockles.databinding.PockListBinding
import com.pes.pockles.model.Pock
import com.pes.pockles.view.ui.pockshistory.item.BindingPockItem

class BottomSheetsPocks : BottomSheetDialogFragment() {
    private val itemAdapter = ItemAdapter<BindingPockItem>()
    private lateinit var data: List<Pock>
    private lateinit var binding: PockListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflaters = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = PockListBinding.inflate(inflaters, container, true)
        binding.lifecycleOwner = this
        return inflater.inflate(R.layout.pock_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val recyclerView: RecyclerView? = view?.findViewById(R.id.nearPockList)
        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = FastAdapter.with(itemAdapter)
        }

    }

    fun setData(list: List<Pock>) {
        data = list
        updateList()
    }

    private fun updateList() {
        val pockListBinding: List<BindingPockItem> = data.map { pock ->
            val binding =
                BindingPockItem()
            binding.pock = pock
            binding
        }
        //Fill and set the items to the ItemAdapter
        itemAdapter.setNewList(pockListBinding)
    }

}