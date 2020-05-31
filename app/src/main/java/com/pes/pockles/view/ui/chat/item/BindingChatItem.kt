package com.pes.pockles.view.ui.chat.item

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.pes.pockles.R
import com.pes.pockles.databinding.ChatItemBinding
import com.pes.pockles.model.Chat


class BindingChatItem constructor(val chat: Chat) : AbstractBindingItem<ChatItemBinding>() {

    override val type: Int
        get() = R.id.chatItemCard

    override fun bindView(binding: ChatItemBinding, payloads: List<Any>) {
        binding.chat = chat
    }

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ChatItemBinding {
        return ChatItemBinding.inflate(inflater, parent, false)
    }
}