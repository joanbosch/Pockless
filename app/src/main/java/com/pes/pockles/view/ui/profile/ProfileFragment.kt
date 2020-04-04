package com.pes.pockles.view.ui.profile

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.pes.pockles.R
import com.pes.pockles.databinding.FragmentProfileBinding
import com.pes.pockles.view.ui.base.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val viewModel: ProfileViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ProfileViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.user.observe(this, Observer {
            it.let { user ->
                Glide.with(this)
                    .load(user.profileImage)
                    .into(binding.profileImage)

                binding.user = user
            }
        })
    }

    override fun getLayout(): Int {
        return R.layout.fragment_profile
    }
}