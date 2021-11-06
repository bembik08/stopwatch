package com.gb.stopwatch.viewmodel

import androidx.lifecycle.ViewModel
import com.gb.stopwatch.viewmodel.interaction.StopwatchStateHolder
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(
    private val stopwatchStateHolder: StopwatchStateHolder,
) : ViewModel() {
    private val scopeIO: CoroutineScope = CoroutineScope(
        Dispatchers.IO + SupervisorJob()
    )
    private var job: Job? = null
    private val mutableTicker = MutableStateFlow("")
    val ticker: StateFlow<String> = mutableTicker

    fun start() {
        if (job == null) startJob()
        stopwatchStateHolder.start()
    }

    private fun startJob() {
        scopeIO.launch {
            while (isActive) {
                mutableTicker.value = stopwatchStateHolder.getStringTimeRepresentation()
                delay(20)
            }
        }
    }

    fun pause() {
        stopwatchStateHolder.pause()
        stopJob()
    }

    fun stop() {
        stopwatchStateHolder.stop()
        stopJob()
        clearValue()
    }

    private fun stopJob() {
        scopeIO.coroutineContext.cancelChildren()
        job = null
    }

    private fun clearValue() {
        mutableTicker.value = DEFAULT_TIME
    }

    companion object {
        private const val DEFAULT_TIME = "00:00:000"
    }
}