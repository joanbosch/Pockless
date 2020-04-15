package com.pes.pockles.view.ui.pockshistory.item

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.pes.pockles.R
import com.pes.pockles.databinding.PockHistoryItemBinding
import com.pes.pockles.model.Pock
import com.pes.pockles.view.ui.editpock.EditPockActivity

class BindingPockItem : AbstractBindingItem<PockHistoryItemBinding>() {
    var pock: Pock? = null

    override val type: Int
        get() = R.id.card

    override fun bindView(binding: PockHistoryItemBinding, payloads: List<Any>) {
        pock?.let {
            binding.pock = it
        }
        binding.editButton.setOnClickListener {
            it.context.startActivity(Intent(it.context, EditPockActivity::class.java))
        }
        if (System.currentTimeMillis() < pock!!.dateInserted + (20 * 60 * 1000)) {
            binding.editButton.visibility = View.VISIBLE
        }
    }

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): PockHistoryItemBinding {
        return PockHistoryItemBinding.inflate(inflater, parent, false)
    }
}