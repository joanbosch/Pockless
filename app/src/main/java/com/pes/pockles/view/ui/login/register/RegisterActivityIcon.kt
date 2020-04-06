package com.pes.pockles.view.ui.login.register

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BasicGridItem
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.bottomsheets.gridItems
import com.afollestad.materialdialogs.bottomsheets.setPeekHeight
import com.bumptech.glide.Glide
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.google.android.material.snackbar.Snackbar
import com.pes.pockles.R
import com.pes.pockles.data.Resource
import com.pes.pockles.databinding.ActivityRegister2Binding
import com.pes.pockles.model.CreateUser
import com.pes.pockles.util.livedata.EventObserver
import com.pes.pockles.view.ui.MainActivity
import com.pes.pockles.view.ui.base.BaseActivity
import java.io.FileNotFoundException
import java.io.InputStream


class RegisterActivityIcon : BaseActivity() {

    private val viewModel: RegisterIconViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(RegisterIconViewModel::class.java)
    }

    private lateinit var binding: ActivityRegister2Binding
    private var preventMultipleClicks = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_2)

        binding.changeIconButton.setOnClickListener {
            photoPicker()
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.registerButton.setOnClickListener {
            if (!preventMultipleClicks) {
                preventMultipleClicks = true
                binding.registerButton.showProgress {
                    progressColor = Color.WHITE
                    buttonTextRes = R.string.registering
                }
                viewModel.registerUser().observe(this, EventObserver(::registerUser))
            }
        }

        bindProgressButton(binding.registerButton)
        binding.registerButton.attachTextChangeAnimator()

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

    private fun registerUser(b: Boolean) {
        preventMultipleClicks = false
        binding.registerButton.hideProgress(R.string.register_create_profile_button_text)
        if (b) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            Snackbar.make(
                binding.containerRegister2,
                "Ha ocurrido un error al crear el perfil. Intentelo de nuevo más tarde",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun photoPicker() {
        val items = listOf(
            BasicGridItem(R.drawable.ic_icon_camera, "Tomar foto"),
            BasicGridItem(R.drawable.ic_image, "Seleccionar foto")
        )

        MaterialDialog(this, BottomSheet()).show {
            title(text = "Subir imagen")
            cornerRadius(16f)
            setPeekHeight(res = R.dimen.register_menu_peek_height)
            gridItems(items) { _, index, _ ->
                run {
                    if (index == 0) {
                        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                            takePictureIntent.resolveActivity(packageManager)?.also {
                                startActivityForResult(takePictureIntent, 112)
                            }
                        }
                    } else {
                        val photoPickerIntent = Intent(Intent.ACTION_PICK)
                        photoPickerIntent.type = "image/*"
                        startActivityForResult(photoPickerIntent, 111)
                    }
                }
            }
        }
    }

    private fun setPhoto(bitmap: Bitmap) {
        viewModel.uploadMedia(bitmap).observe(this, Observer {
            when (it) {
                is Resource.Loading -> binding.loadingView.visibility = View.VISIBLE
                is Resource.Error -> {
                    binding.loadingView.visibility = View.GONE
                    Snackbar.make(
                        binding.containerRegister2,
                        "Ha ocurrido un error al subir la imagen",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                is Resource.Success<String> -> {
                    binding.circularImageView.setImageBitmap(bitmap)
                    binding.loadingView.visibility = View.GONE
                    viewModel.setImageUrl(it.data)
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
                        binding.containerRegister2,
                        "Ha ocurrido un error al seleccionar la imagen",
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
}