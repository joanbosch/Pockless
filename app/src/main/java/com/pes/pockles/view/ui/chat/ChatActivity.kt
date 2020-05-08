package com.pes.pockles.view.ui.chat

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.pes.pockles.R
import com.pes.pockles.data.Resource
import com.pes.pockles.databinding.ChatActivityBinding
import com.pes.pockles.model.Message
import com.pes.pockles.model.NewMessage
import com.pes.pockles.view.ui.base.BaseActivity
import com.pes.pockles.view.ui.chat.item.MessageAdapter

class ChatActivity : BaseActivity() {

    private lateinit var adapter: MessageAdapter

    private lateinit var binding: ChatActivityBinding
    private val viewModel: ChatViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ChatViewModel::class.java)
    }

    private var chatID:String  = ""
    private var pockID:String  = ""
    private var username:String  = ""
    private var userImage: String = ""
    private var chatPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.chat_activity)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        //Add back button to toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        //Obtaing the chatID
        chatID = intent.getStringExtra("chatID")

        /*//Obtaining the pockID
        pockID = intent.getStringExtra("pockID")

        //Obtaining the username of the User
        username = intent.getStringExtra("username")

        // Obtain the user image
        userImage = intent.getStringExtra("userImage")
        */

        //Initialize observers
        initializeObservers()

        //Define Actions
        initializeListeners()

        viewModel.refreshMessages(chatID)
    }

    private fun initializeListeners() {
        //Listener to go Back
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.btnSend.setOnClickListener {
            val txt = binding.txtMessage.text.toString()
            val message = NewMessage(txt,chatID)
            viewModel.postMessage(message)
            binding.txtMessage.text!!.clear()
        }
    }


    private fun initializeObservers() {
        viewModel.messages.observe(this, Observer {
            it?.let {
                when(it) {
                    is Resource.Success<List<Message>> -> setDataRecyclerView(it.data!!)
                    is Resource.Error -> handleError("No se han podido obtener los mensajes del chat")
                }
            }
        }
        )

        viewModel.newMsg.observe(this, Observer {
            it?.let {
                when(it) {
                    is Resource.Success<Message> -> refreshMessages()
                    is Resource.Error -> handleError("No se ha podido añadir el mensaje")
                }
            }
        }
        )
    }

    private fun refreshMessages() {
        viewModel.refreshMessages(chatID)
    }

    private fun handleError(s: String) {
        val text = getString(R.string.cannot_load_chats)
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(this, s, duration)
        toast.show()
    }

    private fun setDataRecyclerView(messages: List<Message>) {
        binding.rvChat.layoutManager = LinearLayoutManager(this)
        adapter = MessageAdapter(this)
        adapter.setMessages(messages)
        binding.rvChat.adapter = adapter
        binding.rvChat.scrollToPosition(messages.size - 1);
        chatPosition = messages.size - 1
    }


}