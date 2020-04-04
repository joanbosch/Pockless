package com.pes.pockles.view.ui.profile

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.pes.pockles.R
import com.pes.pockles.databinding.FragmentProfileBinding
import com.pes.pockles.util.livedata.EventObserver
import com.pes.pockles.view.ui.base.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val viewModel: ProfileViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ProfileViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        viewModel.user.observe(this, Observer {
            it.let { user ->
                Glide.with(this)
                    .load(user.profileImage)
                    .into(binding.profileImage)

                binding.user = user
            }
        })

        viewModel.navigateToHistory.observe(
            this,
            EventObserver(::navigateToHistory)
        )
    }

    private fun navigateToHistory(bool: Boolean) {
        if (bool) {
            findNavController().navigate(R.id.action_userProfileFragment_to_pocksHistoryActivity)
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_profile
    }
}