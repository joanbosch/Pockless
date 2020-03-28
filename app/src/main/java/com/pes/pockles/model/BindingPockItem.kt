package com.pes.pockles.model

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.pes.pockles.R
import com.pes.pockles.databinding.PockHistoryItemBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


//  Here must be PockHistoryItemBinding or the activity binding?
class BindingPockItem : AbstractBindingItem<PockHistoryItemBinding>() {
    var pock: Pock? = null

    override val type: Int
        get() = R.id.card

    override fun bindView(binding: PockHistoryItemBinding, payloads: List<Any>) {
        binding.cardCategory.text = pock?.category
        binding.cardMessage.text = pock?.message
        binding.cardDate.text = getTime(pock?.dateInserted)
    }

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): PockHistoryItemBinding {
        return PockHistoryItemBinding.inflate(inflater, parent, false)
    }

    fun getTime(dateInserted: Long?): String? {
        val df: DateFormat = SimpleDateFormat(
            "dd-MMM-yyyy HH:mm",
            Locale.getDefault()
        )
        return try {
            df.format(dateInserted)
        } catch (ignore: Exception) {
            ""
        }
    }
}