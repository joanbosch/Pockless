package com.pes.pockles.view.ui.chat

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.pes.pockles.R
import com.pes.pockles.data.Resource
import com.pes.pockles.databinding.ChatActivityBinding
import com.pes.pockles.model.Message
import com.pes.pockles.view.ui.base.BaseActivity
import com.pes.pockles.view.ui.chat.item.BindingMessageItem
import com.pes.pockles.view.ui.pockshistory.item.BindingPockItem

class ChatActivity : BaseActivity() {

    private lateinit var binding: ChatActivityBinding
    private val viewModel: ChatViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ChatViewModel::class.java)
    }

    // Create the ItemAdapter holding your Items
    private val itemAdapter = ItemAdapter<BindingMessageItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        //RecyclerView
        binding.rvChat.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = FastAdapter.with(itemAdapter)
        }
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
        val pockListBinding: List<BindingMessageItem> = messages.map { msg ->
            val binding =
                BindingMessageItem()
            binding.message = msg
            binding.myMessage = true
            binding
        }

        //Fill and set the items to the ItemAdapter
        itemAdapter.setNewList(pockListBinding)
    }

    private fun handleError() {
        val text = getString(R.string.cannot_load_chats)
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(this, text, duration)
        toast.show()
    }
}