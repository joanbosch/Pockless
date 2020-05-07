package com.pes.pockles.view.ui.chat

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pes.pockles.R
import com.pes.pockles.data.Resource
import com.pes.pockles.databinding.ChatActivityBinding
import com.pes.pockles.model.Message
import com.pes.pockles.view.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_register.*

class ChatActivity : BaseActivity() {

    private lateinit var binding: ChatActivityBinding
    private val viewModel: ChatViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ChatViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = DataBindingUtil.setContentView(this, R.layout.chat_activity)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        //Add back button to toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        //Initialize observers
        initializeObservers()

        //Define Actions

        /*
        MUST BE DONE!
         */
    }

    private fun initializeObservers() {
        viewModel.messages.observe(this, Observer {
            it?.let {
                when(it) {
                    is Resource.Success<List<Message>> -> setDataRecyclerView(it.data!!)
                    is Resource.Error -> handleError()
                }
            }
        }
        )
    }

    private fun setDataRecyclerView(messages: List<Message>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun handleError() {
        val text = getString(R.string.cannot_load_chats)
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(this, text, duration)
        toast.show()
    }
}