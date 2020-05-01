package com.pes.pockles.data.messaging

enum class Notification {
    CHAT() {
        override fun onMessageRecieved() {
            TODO("not implemented")
        }

    },
    TRENDING() {
        override fun onMessageRecieved() {
            TODO("not implemented")
        }

    },
    REPORTS() {
        override fun onMessageRecieved() {
            TODO("not implemented")
        }

    },
    ACHIEVEMENT() {
        override fun onMessageRecieved() {
            TODO("not implemented")
        }

    },
    BAN() {
        override fun onMessageRecieved() {
            TODO("not implemented")
        }

    };

    abstract fun onMessageRecieved()
}