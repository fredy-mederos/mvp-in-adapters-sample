package com.sample.experiments.ui.items.timer

import com.google.common.truth.Truth
import com.sample.experiments.R
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.text.SimpleDateFormat
import java.util.*

class SingleEngineTimerUIModelTest{


    val timeProvider = TimeProviderTestImpl()
    private val formatter = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)


    @AfterEach
    fun tearDown(){
        timeProvider.currentTime = 0
    }

    @Test
    fun `shows correct data upon creation`(){
        timeProvider.currentTime = 1000
        val endDate = Date(2000)


        val model = SingleEngineTimerUIModel(endDate.time, endDate, timeProvider)

        val endTime = "SE= Ends at: " + formatter.format(endDate)
        Truth.assertThat(model.endsAt).isEqualTo(endTime)

        Truth.assertThat(model.finishedLabelColor).isEqualTo(R.color.red)

        Truth.assertThat(model.time).isEqualTo(1.toString() + " seconds")
    }

    @Test
    fun `ticks the timer with the correct values the on the callback`(){
        timeProvider.currentTime = 1000
        val endDate = Date(3000)


        val model = SingleEngineTimerUIModel(endDate.time, endDate, timeProvider)

        timeProvider.currentTime = 2000

        model.listenToTimeChanges { remaining, finishedColor ->
            val endTime = "SE= Ends at: " + formatter.format(endDate)
            Truth.assertThat(model.endsAt).isEqualTo(endTime)

            Truth.assertThat(model.finishedLabelColor).isEqualTo(R.color.red)

            Truth.assertThat(model.time).isEqualTo(1.toString() + " seconds")

        }

        model.tick()
    }

    @Test
    fun `shows finished state when when time runs out`(){
        timeProvider.currentTime = 1000
        val endDate = Date(3000)


        val model = SingleEngineTimerUIModel(endDate.time, endDate, timeProvider)

        timeProvider.currentTime = 3000

        model.listenToTimeChanges { remaining, finishedColor ->
            val endTime = "SE= Ends at: " + formatter.format(endDate)
            Truth.assertThat(model.endsAt).isEqualTo(endTime)

            Truth.assertThat(model.finishedLabelColor).isEqualTo(R.color.green)

            Truth.assertThat(model.time).isEqualTo("Finish")

        }

        model.tick()
    }
}