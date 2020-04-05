package com.pes.pockles.view.ui.login.register

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.pes.pockles.R
import com.pes.pockles.databinding.ActivityRegister2Binding
import com.pes.pockles.model.CreateUser
import com.pes.pockles.view.ui.base.BaseActivity
import timber.log.Timber

class RegisterActivityIcon : BaseActivity() {

    private val viewModel: RegisterIconViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(RegisterIconViewModel::class.java)
    }

    private lateinit var binding: ActivityRegister2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_2)

        binding.changeIconButton.setOnClickListener {
            Toast.makeText(this, "FUCK YOU", Toast.LENGTH_SHORT).show()
        }

        val user: CreateUser? = intent.extras?.getParcelable("createUser")
        user?.let {
            viewModel.setUser(it)
        }

        viewModel.user.value?.let {
            Glide.with(this)
                .load(it.profileImageUrl)
                .into(binding.circularImageView)
        }

    }
}