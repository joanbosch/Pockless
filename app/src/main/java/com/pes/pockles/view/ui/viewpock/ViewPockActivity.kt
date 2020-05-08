package com.pes.pockles.view.ui.viewpock

import android.content.Intent
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.pes.pockles.R
import com.pes.pockles.databinding.ViewPockBinding
import com.pes.pockles.view.ui.base.BaseActivity
import com.pes.pockles.view.ui.chat.ChatActivity


class ViewPockActivity : BaseActivity() {

    private lateinit var binding: ViewPockBinding
    private val viewModel: ViewPockViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ViewPockViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pockId: String? = intent.extras?.getString("markerId")

        if (pockId == null) {
            finish()
        }

        setUpWindow()

        binding = DataBindingUtil.setContentView(this, R.layout.view_pock)

        binding.pockViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.loadPock(pockId!!)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.chat.setOnClickListener {
            goChat(pockId)
        }

        binding.share.setOnClickListener {
            sharePock()
        }

        binding.report.setOnClickListener {
            goReport()
        }
    }

    private fun sharePock() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, viewModel.getPock()?.message)
        startActivity(shareIntent)
    }

    private fun goReport() {
        Toast.makeText(this, "Report function not implemented yet!", Toast.LENGTH_SHORT).show()
    }

    private fun goChat(pockId: String) {

        val intent = Intent(this, ChatActivity::class.java).apply {
            putExtra("pockID", pockId)
            putExtra("username", viewModel.pock.value!!.data?.username)
            putExtra("userImage", viewModel.pock.value!!.data?.userProfileImage)
            //putExtra("editableContent", EditedPock(pock!!.message, pock!!.category, pock!!.chatAccess, pock?.media) as Serializable)
        }
        startActivity(intent)

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