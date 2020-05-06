package com.pes.pockles.view.ui.chat.item

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.pes.pockles.R
import com.pes.pockles.databinding.ChatItemBinding
import com.pes.pockles.model.Chat


class BindingChatItem : AbstractBindingItem<ChatItemBinding>() {
    public var chat: Chat? = null

    override val type: Int
        get() = R.id.chatItemCard

    override fun bindView(binding: ChatItemBinding, payloads: List<Any>) {
        chat?.let {
            binding.chat = it
        }

        /*
    binding.chatItemCard.setOnClickListener {

        val intent = Intent(it.context, EditPockActivity::class.java).apply {
            putExtra("pockId", pock?.id)
            putExtra("editableContent", EditedPock(pock!!.message, pock!!.category, pock!!.chatAccess, pock?.media) as Serializable)
        }
        it.context.startActivity(intent)
    }
    if (System.currentTimeMillis() < pock!!.dateInserted + (4000 * 60 * 1000)) {
        binding.editButton.visibility = View.VISIBLE
    }
         */
    }

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ChatItemBinding {
        return ChatItemBinding.inflate(inflater, parent, false)
    }
}