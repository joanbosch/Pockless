package com.pes.pockles.view.ui.editprofile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BasicGridItem
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.bottomsheets.gridItems
import com.afollestad.materialdialogs.bottomsheets.setPeekHeight
import com.bumptech.glide.Glide
import com.firebase.ui.auth.AuthUI
import com.google.android.material.snackbar.Snackbar
import com.pes.pockles.R
import com.pes.pockles.data.Resource
import com.pes.pockles.databinding.ActivityEditProfileBinding
import com.pes.pockles.model.EditedUser
import com.pes.pockles.view.ui.base.BaseActivity
import com.xw.repo.BubbleSeekBar
import dev.sasikanth.colorsheet.ColorSheet
import java.io.FileNotFoundException
import java.io.InputStream

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

        binding.usernameInfo.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                viewModel.setUsername(s.toString())
                if (s.isNullOrEmpty()) {
                    binding.usernameInfo.setError("Este campo no puede estar vacío")
                }
            }
        })

        binding.accentColorContainer.setOnClickListener {
            ColorSheet().colorPicker(
                colors = resources.getIntArray(R.array.mdcolor_500),
                selectedColor = getSelectedColor(),
                listener = { color ->
                    viewModel.setColor(color)
                })
                .show(supportFragmentManager)
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.takeProfileImage.setOnClickListener {
            photoPicker()
        }

        binding.saveButton.setOnClickListener {
            if (!viewModel.editableContent.value?.name.isNullOrEmpty()) {
                viewModel.save()
                Log.i("EditingProfile", viewModel.editableContent.value?.name)
                Log.i("EditingProfile", viewModel.editableContent.value?.accentColor)
                Log.i("EditingProfile", viewModel.editableContent.value?.radiusVisibility.toString())
                Log.i("EditingProfile", viewModel.editableContent.value?.profileImage)
            }
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

    private fun photoPicker() {
        val items = listOf(
            BasicGridItem(R.drawable.ic_icon_camera, getString(R.string.take_photo_dialog_option)),
            BasicGridItem(R.drawable.ic_image, getString(R.string.select_photo_dialog_option)),
            BasicGridItem(R.drawable.ic_delete, getString(R.string.delete))
        )

        MaterialDialog(this, BottomSheet()).show {
            title(text = getString(R.string.upload_image_dialog_title))
            cornerRadius(16f)
            setPeekHeight(res = R.dimen.register_menu_peek_height)
            gridItems(items) { _, index, _ ->
                run {
                    when (index) {
                        0 -> {
                            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                                takePictureIntent.resolveActivity(packageManager)?.also {
                                    startActivityForResult(takePictureIntent, 112)
                                }
                            }
                        }
                        1 -> {
                            val photoPickerIntent = Intent(Intent.ACTION_PICK)
                            photoPickerIntent.type = "image/*"
                            startActivityForResult(photoPickerIntent, 111)
                        }
                        else -> {
                            viewModel.deleteImage()
                        }
                    }
                }
            }
        }
    }

    private fun setPhoto(bitmap: Bitmap) {
        viewModel.uploadMedia(bitmap).observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.loadingView.visibility = View.VISIBLE
                    binding.profileImage.visibility = View.GONE
                    binding.takeProfileImage.visibility = View.GONE
                }
                is Resource.Error -> {
                    binding.loadingView.visibility = View.GONE
                    binding.profileImage.visibility = View.VISIBLE
                    binding.takeProfileImage.visibility = View.VISIBLE
                    Snackbar.make(
                        binding.editProfile,
                        getString(R.string.error_uploading_an_image),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                is Resource.Success<String> -> {
                    binding.loadingView.visibility = View.GONE
                    viewModel.setImageUrl(it.data!!)
                    binding.profileImage.visibility = View.VISIBLE
                    binding.takeProfileImage.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)
        if (reqCode == 111) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    val imageUri: Uri? = data?.data
                    val imageStream: InputStream? = contentResolver.openInputStream(imageUri!!)
                    val selectedImage = BitmapFactory.decodeStream(imageStream)
                    setPhoto(selectedImage)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                    Snackbar.make(
                        binding.editProfile,
                        getString(R.string.error_selecting_image),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        } else if (reqCode == 112) {
            if (resultCode == Activity.RESULT_OK) {
                val imageBitmap = data?.extras?.get("data") as Bitmap
                setPhoto(imageBitmap)
            }
        }
    }

    private fun close() {
        run {
            AuthUI.getInstance().delete(this@EditProfileActivity)
            finish()
        }
    }

    override fun onBackPressed() {
        Log.i("editing profile back", viewModel.isChanged().toString())
        if (viewModel.isChanged()) {
            AlertDialog.Builder(this).setTitle(getString(R.string.cancel_changes_dialog_title))
                .setMessage(getString(R.string.cancel_changes_dialog_description))
                .setPositiveButton(
                    getString(R.string.yes)
                ) { _, _ ->
                    close()
                }
                .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }
        else close()
    }
}
