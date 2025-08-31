package com.mak.androidmvi.ui.core

sealed interface Loggable {
    fun log()
}

class FileLogger : Loggable {
    override fun log() {
        // Implementation for file logging
    }
}

class DatabaseLogger : Loggable {
    override fun log() {
        // Implementation for database logging
    }
}

// Usage example:
fun logAll(loggables: List<Loggable>) {
    loggables.forEach { it.log() }
}