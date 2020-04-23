package com.pes.pockles.view.ui.pockshistory.item

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.pes.pockles.R
import com.pes.pockles.databinding.PockHistoryItemBinding
import com.pes.pockles.model.EditedPock
import com.pes.pockles.model.Pock
import com.pes.pockles.view.ui.editpock.EditPockActivity
import java.io.Serializable

class BindingPockItem : AbstractBindingItem<PockHistoryItemBinding>() {
    var pock: Pock? = null

    override val type: Int
        get() = R.id.card

    override fun bindView(binding: PockHistoryItemBinding, payloads: List<Any>) {
        pock?.let {
            binding.pock = it
        }
        binding.editButton.setOnClickListener {
            val intent = Intent(it.context, EditPockActivity::class.java).apply {
                putExtra("pockId", pock?.id)
                putExtra("editableContent", EditedPock(pock!!.message, pock!!.category, pock!!.chatAccess, pock?.media) as Serializable)
            }
            it.context.startActivity(intent)
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