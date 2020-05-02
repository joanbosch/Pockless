package com.pes.pockles.data.messaging

enum class Notification {
    CHAT {
        override fun onMessageReceived(messageBody: String?, title: String?): Boolean {
            return false
        }

    },
    TRENDING {
        override fun onMessageReceived(messageBody: String?, title: String?): Boolean {
            return false
        }

    },
    REPORTS {
        override fun onMessageReceived(messageBody: String?, title: String?): Boolean {
            return false
        }

    },
    ACHIEVEMENT {
        override fun onMessageReceived(messageBody: String?, title: String?): Boolean {
            return false
        }

    },
    BAN {
        override fun onMessageReceived(messageBody: String?, title: String?): Boolean {
            return false
        }

    },
    DEFAULT {
        override fun onMessageReceived(messageBody: String?, title: String?): Boolean {
            return false
        }

    };

    abstract fun onMessageReceived(messageBody: String?, title: String?): Boolean


}