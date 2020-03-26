package com.pes.pockles.model

import android.view.LayoutInflater
import android.view.ViewGroup
import com.pes.pockles.R

class BindingPockItem : AbstractBindingItem<BindingPockItemBin>() {
    var pock: Pock? = null

    override val type: Int
        get() = R.id.fastadapter_icon_item_id

    override fun bindView(binding: IconItemBinding, payloads: List<Any>) {
        binding.pock = pock
    }

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): IconItemBinding {
        return IconItemBinding.inflate(inflater, parent, false)
    }
}