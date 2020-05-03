package com.pes.pockles.view.ui.editprofile

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.pes.pockles.R
import com.pes.pockles.databinding.ActivityEditProfileBinding
import com.pes.pockles.view.ui.base.BaseActivity
import dagger.android.AndroidInjection

class EditProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    private val viewModel: EditProfileViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(EditProfileViewModel::class.java)
    }
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
     }

}
