package com.pes.pockles.view.ui.viewpock

import android.content.Intent
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pes.pockles.R
import com.pes.pockles.data.Resource
import com.pes.pockles.databinding.ViewPockBinding
import com.pes.pockles.model.ChatData
import com.pes.pockles.model.Pock
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

        initializeObservers()
    }

    private fun initializeObservers() {
        viewModel.pock.observe(this, Observer {
            it?.let {
                when(it) {
                    is Resource.Success<Pock> -> setChatButton()
                }
            }
        }
        )
    }

    private fun setChatButton() {
        if (!viewModel.getPock()!!.chatAccess!!) {
            binding.chat.visibility = View.GONE
        }
    }

    private fun sharePock() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, viewModel.getPock()?.message)
        startActivity(shareIntent)
    }

    private fun goReport() {
        basicAlert()
    }

    private fun goChat(pockId: String) {

        val intent = Intent(this, ChatActivity::class.java).apply {
            var chatData: ChatData = ChatData(null, pockId, viewModel.pock.value?.data!!.username, viewModel.pock.value?.data!!.userProfileImage)
            putExtra("chatData", chatData)
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

    //Alert for displaying the user agreement to report the pock
    private fun basicAlert() {
        let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.alertMessageReport)?.setTitle(R.string.alertTitleReport)
            builder.apply {
                setPositiveButton(
                    R.string.alertOK
                ) { dialog, id ->
                    choiceAlert()
                    // User clicked OK button
                }
                setNegativeButton(
                    R.string.alertNO
                ) { dialog, id ->
                    // User cancelled the dialog, it simply closes it
                }

            }
            builder.create()
        }.show()
    }

    private fun choiceAlert() {
        // setup the alert builder
        val builder =
            AlertDialog.Builder(this)
        builder.setTitle(R.string.alertTitleMotivo)
// add a radio button list
        val motivos = R.array.motivos
        var checkedItem = 1 // default
        builder.setSingleChoiceItems(
            motivos,
            checkedItem
        ) { dialog, which ->
            checkedItem = which
        }
// add OK and Cancel buttons

        builder.setPositiveButton(
            R.string.alertOK
        ) { dialog, which ->
            okReport(checkedItem)
        }
        builder.setNegativeButton(R.string.alertNO, null)
// create and show the alert dialog
        val dialog = builder.create()
        dialog.show()
    }

    private fun okReport(which: Int) {
        val bigArray = resources.getStringArray(R.array.motivos)
        val motive = bigArray[which]
        viewModel.report(motive)
    }

}