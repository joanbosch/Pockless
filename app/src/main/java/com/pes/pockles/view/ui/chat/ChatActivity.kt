package com.pes.pockles.view.ui.chat

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.pes.pockles.R
import com.pes.pockles.data.Resource
import com.pes.pockles.databinding.ChatActivityBinding
import com.pes.pockles.model.ChatData
import com.pes.pockles.model.Message
import com.pes.pockles.model.NewMessage
import com.pes.pockles.view.ui.base.BaseActivity
import com.pes.pockles.view.ui.chat.item.MessageAdapter
import com.pes.pockles.view.ui.viewuser.ViewUserActivity

class ChatActivity : BaseActivity() {

    private lateinit var adapter: MessageAdapter

    private lateinit var binding: ChatActivityBinding
    private val viewModel: ChatViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ChatViewModel::class.java)
    }

    private lateinit var chatInformation: ChatData
    private lateinit var userId: String
    private var chatPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.chat_activity)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        binding.rvChat.layoutManager = LinearLayoutManager(this)
        adapter = MessageAdapter(this)

        //Add back button to toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        //Obtaining the ChatInformation
        chatInformation = intent.extras?.getParcelable("chatData")!!
        userId = intent.extras?.getString("userId")!!
        binding.chat = chatInformation

        // Set the notification binding to the activity
        chatInformation.chatId?.let { viewModel.setUpNotificationObserver(it) }

        //Initialize observers
        initializeObservers()

        //Define Actions
        initializeListeners()

        chatInformation.chatId?.let { viewModel.refreshMessages(it) }
    }

    private fun initializeListeners() {
        //Listener to go Back
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.btnSend.setOnClickListener {
            val txt = binding.txtMessage.text.toString()
            var message: NewMessage
            message = if (!chatInformation.chatId.isNullOrEmpty()) {
                NewMessage(txt, chatInformation.chatId, null)
            } else {
                NewMessage(txt, null, chatInformation.pockId)
            }
            viewModel.postMessage(message)
            binding.txtMessage.text!!.clear()
        }

        binding.username.setOnClickListener {
            val intent = Intent(it.context, ViewUserActivity::class.java).apply {
                putExtra("userId", userId)
            }
            it.context.startActivity(intent)
        }

        binding.circularImageView2.setOnClickListener {
            val intent = Intent(it.context, ViewUserActivity::class.java).apply {
                putExtra("userId", userId)
            }
            it.context.startActivity(intent)
        }
    }


    private fun initializeObservers() {
        viewModel.messages.observe(this, Observer {
            it?.let {
                when(it) {
                    is Resource.Success<MutableList<Message>> -> setDataRecyclerView(it.data!!)
                    is Resource.Error -> handleError(getString(R.string.cannot_obtain_the_chat_messages))
                }
            }
        }
        )

        viewModel.newMsg.observe(this, Observer {
            it?.let {
                when(it) {
                    is Resource.Success<Message> -> refreshMessages(it.data)
                    is Resource.Error -> handleError(getString(R.string.cannot_add_message))
                }
            }
        }
        )
    }

    private fun handleError(s: String) {
        val text = getString(R.string.cannot_load_chats)
        Snackbar.make(
            binding.constraintLayout3,
            getString(R.string.cannot_load_chats),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun setDataRecyclerView(messages: MutableList<Message>) {
        adapter.setMessages(messages)
        binding.rvChat.adapter = adapter
        binding.rvChat.scrollToPosition(messages.size - 1);
        chatPosition = messages.size - 1
    }

    // Non-used, but can be useful in future
    private fun refreshMessages(msg: Message?) {
        chatInformation.chatId = msg!!.chatId
        chatInformation.chatId?.let { viewModel.refreshMessages(it) }
    }
}