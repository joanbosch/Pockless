package com.pes.pockles.view.ui.chat.item

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.pes.pockles.R
import com.pes.pockles.databinding.OtherMessageBinding
import com.pes.pockles.model.Message

class BindingMessageItem : AbstractBindingItem<OtherMessageBinding>() {
    var message: Message? = null
    var myMessage: Boolean? = null

    override val type: Int
        get() = R.id.myMessage


    override fun bindView(binding: OtherMessageBinding, payloads: List<Any>) {
        message?.let {
            binding.message = it
        }
    }

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): OtherMessageBinding {
        return OtherMessageBinding.inflate(inflater, parent, false)
    }
}