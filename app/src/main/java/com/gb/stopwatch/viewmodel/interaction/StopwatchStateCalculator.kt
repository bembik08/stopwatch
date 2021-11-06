package com.gb.stopwatch.viewmodel.interaction

import com.gb.stopwatch.model.Repository
import com.gb.stopwatch.model.StopwatchState
import com.gb.stopwatch.utils.ElapsedTimeCalculator

class StopwatchStateCalculator(
    private val repository: Repository,
    private val elapsedTimeCalculator: ElapsedTimeCalculator,
) {

    fun calculateRunningState(oldState: StopwatchState): StopwatchState.Running =
        when (oldState) {
            is StopwatchState.Running -> oldState
            is StopwatchState.Paused -> {
                StopwatchState.Running(
                    startTime = repository.getMilliseconds(),
                    elapsedTime = oldState.elapsedTime
                )
            }
        }

    fun calculatePausedState(oldState: StopwatchState): StopwatchState.Paused =
        when (oldState) {
            is StopwatchState.Running -> {
                val elapsedTime = elapsedTimeCalculator.calculate(oldState, oldState.startTime)
                StopwatchState.Paused(elapsedTime = elapsedTime)
            }
            is StopwatchState.Paused -> oldState
        }

    fun getCurrentTime(): Long = repository.getMilliseconds()
}