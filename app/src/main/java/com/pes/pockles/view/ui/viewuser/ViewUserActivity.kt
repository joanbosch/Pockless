package com.pes.pockles.view.ui.viewuser

import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.pes.pockles.R
import com.pes.pockles.databinding.ActivityViewUserBinding
import com.pes.pockles.model.User
import com.pes.pockles.view.ui.base.BaseActivity


class ViewUserActivity : BaseActivity() {

    private lateinit var binding: ActivityViewUserBinding
    private val viewModel: ViewUserViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ViewUserViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId: String? = intent.extras?.getString("markerId")

        if (userId == null) {
            finish()
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_user)

        binding.viewUserViewModel = viewModel
        binding.lifecycleOwner = this

        //viewModel.loadUser(userId!!)

        binding.back.setOnClickListener {
            onBackPressed()
        }
    }
}