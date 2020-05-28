package com.pes.pockles.view.ui.achievements

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.pes.pockles.R
import com.pes.pockles.databinding.AchievementsBinding
import com.pes.pockles.view.ui.achievements.item.BindingAchievementItem
import com.pes.pockles.view.ui.base.BaseActivity
import com.pes.pockles.view.ui.likes.item.BindingLikeItem
import com.pes.pockles.model.Achievement



class AchievementsActivity : BaseActivity() {

    private lateinit var binding: BindingAchievementItem

    private val viewModel: AchievementsViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(AchievementsViewModel::class.java)
    }

    private val itemAdapter = ItemAdapter<BindingLikeItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.achievements)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val fastAdapter = FastAdapter.with(itemAdapter)
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(this@AchievementsActivity)
            adapter = fastAdapter
        }
    }


}