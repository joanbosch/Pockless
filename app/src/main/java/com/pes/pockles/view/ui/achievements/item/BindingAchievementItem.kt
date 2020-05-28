package com.pes.pockles.view.ui.achievements.item

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.pes.pockles.R
import com.pes.pockles.databinding.AchievementsItemBinding
import com.pes.pockles.model.Achievement

class BindingAchievementItem (val achievement: Achievement) : AbstractBindingItem<AchievementsItemBinding>() {

    override val type: Int
        get() = R.id.fastadapter_item

    override fun bindView(binding: AchievementsItemBinding, payloads: List<Any>) {
        binding.achievement = achievement
    }

    override var identifier: Long
        get() = achievement.id.hashCode().toLong()
        set(value) {}

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): AchievementsItemBinding {
        return AchievementsItemBinding.inflate(inflater, parent, false)
    }
}