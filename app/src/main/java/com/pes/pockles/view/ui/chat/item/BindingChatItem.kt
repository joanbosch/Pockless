package com.pes.pockles.view.ui.chat.item

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.pes.pockles.R
import com.pes.pockles.databinding.ChatItemBinding
import com.pes.pockles.model.Chat
import com.pes.pockles.model.ChatData
import com.pes.pockles.view.ui.chat.ChatActivity


class BindingChatItem : AbstractBindingItem<ChatItemBinding>() {
    public var chat: Chat? = null

    override val type: Int
        get() = R.id.chatItemCard

    override fun bindView(binding: ChatItemBinding, payloads: List<Any>) {
        chat?.let {
            binding.chat = it
        }
    }

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ChatItemBinding {
        return ChatItemBinding.inflate(inflater, parent, false)
    }
}