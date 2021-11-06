package com.gb.stopwatch.utils

import com.gb.stopwatch.model.StopwatchState

object ElapsedTimeCalculator {
    fun calculate(state: StopwatchState.Running, currentTime: Long): Long {
        val timePassedSinceStart = if (currentTime > state.startTime) {
            currentTime - state.startTime
        } else {
            0
        }
        return timePassedSinceStart + state.elapsedTime
    }
}