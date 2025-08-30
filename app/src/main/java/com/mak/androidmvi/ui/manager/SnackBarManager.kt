package com.mak.androidmvi.ui.manager

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.receiveAsFlow

object SnackBarManager {

    //using channel
    private val _message_channel = Channel<String>(Channel.BUFFERED)
    val message_channel = _message_channel.receiveAsFlow()

    suspend fun showMessageChannel(message: String){
        _message_channel.send(message)
    }


    private val _message_sharedFlow = MutableSharedFlow<String>(replay = 0)
    val message_sharedFlow : SharedFlow<String> = _message_sharedFlow

    suspend fun showMessageSharedFlow(message: String){
        _message_sharedFlow.emit(message)
    }

}