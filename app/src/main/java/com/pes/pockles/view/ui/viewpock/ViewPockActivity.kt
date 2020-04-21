package com.pes.pockles.view.ui.viewpock

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.pes.pockles.R
import com.pes.pockles.R.*
import com.pes.pockles.data.Resource
import com.pes.pockles.databinding.ViewPockBinding
import com.pes.pockles.model.Pock
import com.pes.pockles.view.ui.base.BaseActivity


class ViewPockActivity : BaseActivity() {

    private lateinit var binding: ViewPockBinding
    private val viewModel: ViewPockViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ViewPockViewModel::class.java)
    }

    private lateinit var pock: Pock

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pockId: String? = intent.extras?.getString("markerId")

        if (pockId == null) {
            finish()
        }

        setUpWindow()

        binding = DataBindingUtil.setContentView(this, layout.view_pock)

        binding.loading.visibility = View.VISIBLE

        binding.pockViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.loadPock(pockId!!).observe(
            this,
            Observer { value: Resource<Pock>? ->
                value?.let {
                    when (value) {
                        is Resource.Success<Pock> -> {
                            this.pock = value.data
                            binding.pock = this.pock
                            downloadMedia()
                            binding.loading.visibility = View.GONE
                        }
                    }
                }
            }
        )
        viewModel.goBack.observe(this, Observer<Boolean> { backButtonPressed ->
            if (backButtonPressed) goBack()
        })

        viewModel.goShare.observe(this, Observer<Boolean> { shareButtonPressed ->
            if (shareButtonPressed) goShare()
        })

        viewModel.goReport.observe(this, Observer<Boolean> { reportButtonPressed ->
            if (reportButtonPressed) goReport()
        })

        viewModel.goChat.observe(this, Observer<Boolean> { chatButtonPressed ->
            if (chatButtonPressed) goChat()
        })

    }

    private fun downloadMedia() {
        var k: Int = 0
        if (pock.media != null) {
            for (url in pock.media!!) {
                insertImage(url, k)
                k++
            }
        }
    }

    private fun insertImage(url: String, k: Int) {
        when(k) {
            0 -> {
                Glide.with(this).load(url).into(binding.pockImage0)
                binding.pockImage0.visibility = View.VISIBLE
            }
            1 -> {
                Glide.with(this).load(url).into(binding.pockImage1)
                binding.pockImage1.visibility = View.VISIBLE
            }
            2 -> {
                Glide.with(this).load(url).into(binding.pockImage2)
                binding.pockImage2.visibility = View.VISIBLE
            }
            3 -> {
                Glide.with(this).load(url).into(binding.pockImage3)
                binding.pockImage3.visibility = View.VISIBLE
            }
        }
    }

    private fun goBack() {
        onBackPressed()
    }

    private fun goShare() {
        shareSuccess()
    }

    private fun goReport() {
        Toast.makeText(this, "Report function not implemented yet!", Toast.LENGTH_SHORT).show()
    }

    private fun goChat() {
        Toast.makeText(this, "Chat function not implemented yet!", Toast.LENGTH_SHORT).show()
    }

    // Starting an Activity with our new Intent
    private fun shareSuccess() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, this.pock.message)
        startActivity(shareIntent)
    }

    private fun setUpWindow() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_DIM_BEHIND,
            WindowManager.LayoutParams.FLAG_DIM_BEHIND
        )
        val params = window.attributes
        params.alpha = 1.0f // lower than one makes it more transparent
        params.dimAmount = .6f
        window.attributes = params
        window.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    android.R.color.transparent
                )
            )
        )
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width: Int = size.x
        val height: Int = size.y
        if (height > width) {
            window.setLayout((width * .9).toInt(), (height * .9).toInt())
        } else {
            window.setLayout((width * .7).toInt(), (height * .8).toInt())
        }
    }
}