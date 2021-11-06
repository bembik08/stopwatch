package com.gb.stopwatch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gb.stopwatch.databinding.ActivityMainBinding
import com.gb.stopwatch.model.RepositoryImpl
import com.gb.stopwatch.utils.ElapsedTimeCalculator
import com.gb.stopwatch.utils.TimestampMillisecondsFormatter
import com.gb.stopwatch.viewmodel.MainViewModel
import com.gb.stopwatch.viewmodel.interaction.StopwatchStateCalculator
import com.gb.stopwatch.viewmodel.interaction.StopwatchStateHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewBinding: ActivityMainBinding by viewBinding(CreateMethod.INFLATE)
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val mainViewModelFirstwatcher by lazy {
        MainViewModel(
            StopwatchStateHolder(
                StopwatchStateCalculator(
                    RepositoryImpl(),
                    ElapsedTimeCalculator
                ),
                ElapsedTimeCalculator,
                TimestampMillisecondsFormatter
            )
        )
    }
    private val mainViewModelSecondwatcher by lazy {
        MainViewModel(
            StopwatchStateHolder(
                StopwatchStateCalculator(
                    RepositoryImpl(),
                    ElapsedTimeCalculator
                ),
                ElapsedTimeCalculator,
                TimestampMillisecondsFormatter
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        scope.launch {
            mainViewModelFirstwatcher.ticker.collect {
                viewBinding.textTime.text = it
            }
        }
        scope.launch {
            mainViewModelSecondwatcher.ticker.collect {
                viewBinding.textSecondTime.text = it
            }
        }

        viewBinding.buttonStart.setOnClickListener {
            mainViewModelFirstwatcher.start()
        }
        viewBinding.buttonPause.setOnClickListener {
            mainViewModelFirstwatcher.pause()
        }
        viewBinding.buttonStop.setOnClickListener {
            mainViewModelFirstwatcher.stop()
        }
        viewBinding.buttonSecondStart.setOnClickListener {
            mainViewModelSecondwatcher.start()
        }
        viewBinding.buttonSecondPause.setOnClickListener {
            mainViewModelSecondwatcher.pause()
        }
        viewBinding.buttonSecondStop.setOnClickListener {
            mainViewModelSecondwatcher.stop()
        }

    }
}















