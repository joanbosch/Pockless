package com.pes.pockles.view.ui.achievements

import BindingAchievements
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.FastAdapter
import com.pes.pockles.R
import com.pes.pockles.data.Resource
import com.pes.pockles.model.Pock
import com.pes.pockles.view.ui.base.BaseActivity
import com.pes.pockles.view.ui.pockshistory.item.BindingPockItem

class AchievementsActivity : BaseActivity() {

    private lateinit var binding: BindingAchievements
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}