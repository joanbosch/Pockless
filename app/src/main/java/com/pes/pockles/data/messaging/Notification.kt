package com.pes.pockles.data.messaging

import android.app.ActivityManager

import com.pes.pockles.data.repository.RepositoryProvider
import com.pes.pockles.model.Message
import com.pes.pockles.view.ui.chat.ChatActivity

enum class Notification {
    CHAT {
        override fun onMessageReceived(
            repositoryProvider: RepositoryProvider,
            messageBody: String?,
            title: String?,
            extras: Map<String, String>
        ): Boolean {
            val msg = Message(extras["chatId"] ?: error(""),
                extras["text"] ?: error(""),
                extras["senderId"] ?: error(""),
                (extras["read"] ?: error("")).toBoolean(),
                (extras["date"] ?: error("")).toLong(),
                extras["chatId"] ?: error("")
            )
            return repositoryProvider.chatRepository.onMessageReceived(msg)
        }

    },
    TRENDING {
        override fun onMessageReceived(
            repositoryProvider: RepositoryProvider,
            messageBody: String?,
            title: String?,
            extras: Map<String, String>
        ): Boolean {
            return false
        }

    },
    REPORTS {
        override fun onMessageReceived(
            repositoryProvider: RepositoryProvider,
            messageBody: String?,
            title: String?,
            extras: Map<String, String>
        ): Boolean {
            return false
        }

    },
    ACHIEVEMENT {
        override fun onMessageReceived(
            repositoryProvider: RepositoryProvider,
            messageBody: String?,
            title: String?,
            extras: Map<String, String>
        ): Boolean {
            return false
        }

    },
    BAN {
        override fun onMessageReceived(
            repositoryProvider: RepositoryProvider,
            messageBody: String?,
            title: String?,
            extras: Map<String, String>
        ): Boolean {
            return false
        }

    },
    DEFAULT {
        override fun onMessageReceived(
            repositoryProvider: RepositoryProvider,
            messageBody: String?,
            title: String?,
            extras: Map<String, String>
        ): Boolean {
            return false
        }

    };

    abstract fun onMessageReceived(
        repositoryProvider: RepositoryProvider,
        messageBody: String?,
        title: String?,
        extras: Map<String, String>
    ): Boolean


}