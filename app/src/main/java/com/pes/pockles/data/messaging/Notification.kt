package com.pes.pockles.data.messaging

import com.pes.pockles.data.repository.RepositoryProvider

enum class Notification {
    CHAT {
        override fun onMessageReceived(
            repositoryProvider: RepositoryProvider,
            messageBody: String?,
            title: String?,
            extras: Map<String, String>
        ): Boolean {
            return false
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