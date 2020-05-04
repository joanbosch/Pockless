package com.pes.pockles.view.ui.editprofile

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.pes.pockles.R
import com.pes.pockles.databinding.ActivityEditProfileBinding
import com.pes.pockles.model.EditedUser
import com.pes.pockles.view.ui.base.BaseActivity
import com.xw.repo.BubbleSeekBar
import dev.sasikanth.colorsheet.ColorSheet

class EditProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    private val viewModel: EditProfileViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(EditProfileViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
        binding.editProfileViewModel = viewModel
        binding.lifecycleOwner = this

        loadContent()

        viewModel.editableContent.observe(this, Observer {
            it?.let { user ->
                Glide.with(this)
                    .load(user.profileImage)
                    .into(binding.profileImage)
                //binding.user = user
            }
        })

        binding.visibilitySeekBar.onProgressChangedListener = object :
            BubbleSeekBar.OnProgressChangedListener {
            override fun onProgressChanged(
                bubbleSeekBar: BubbleSeekBar?,
                progress: Int,
                progressFloat: Float,
                fromUser: Boolean
            ) {
                viewModel.setVisibility(progressFloat)
            }

            override fun getProgressOnActionUp(
                bubbleSeekBar: BubbleSeekBar?,
                progress: Int,
                progressFloat: Float
            ) {
            }

            override fun getProgressOnFinally(
                bubbleSeekBar: BubbleSeekBar?,
                progress: Int,
                progressFloat: Float,
                fromUser: Boolean
            ) {
            }

        }

        binding.accentColorContainer.setOnClickListener {
            ColorSheet().colorPicker(
                colors = resources.getIntArray(R.array.mdcolor_500),
                selectedColor = getSelectedColor(),
                listener = { color ->
                    viewModel.setColor(color)
                })
                .show(supportFragmentManager)
        }

        binding.saveButton.setOnClickListener {
            Log.i("EditingProfile", viewModel.editableContent.value?.name)
            Log.i("EditingProfile", viewModel.editableContent.value?.accentColor)
            Log.i("EditingProfile", viewModel.editableContent.value?.radiusVisibility.toString())
            Log.i("EditingProfile", viewModel.editableContent.value?.profileImage)
        }
    }

    private fun loadContent() {
        val infoToEdit = intent.extras?.get("editableContent") as EditedUser
        viewModel.loadContent(intent.extras?.get("mail") as String, intent.extras?.get("birthDate") as String, infoToEdit)
        binding.visibilitySeekBar.setProgress(infoToEdit.radiusVisibility)
    }

    private fun getSelectedColor(): Int? {
        val color = viewModel.editableContent.value?.accentColor ?: return null
        return Color.parseColor(color)
    }

}
