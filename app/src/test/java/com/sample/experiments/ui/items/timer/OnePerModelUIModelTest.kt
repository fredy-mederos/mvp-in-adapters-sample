package com.sample.experiments.ui.items.timer

import com.google.common.truth.Truth
import com.sample.experiments.R
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.text.SimpleDateFormat
import java.util.*

class OnePerModelUIModelTest{

    val timeProvider = TimeProviderTestImpl()
    private val formatter = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)


    @Test
    fun `shows correct time upon creation`(){
        timeProvider.currentTime = 1000
        val endDate = Date(2000)


        val model = OnePerModelUIModel(endDate, timeProvider)


        val endTime = "OPM= Ends at: " + formatter.format(endDate)
        Truth.assertThat(model.endsAt).isEqualTo(endTime)

        Truth.assertThat(model.finishedLabelColor).isEqualTo(R.color.red)

        Truth.assertThat(model.time).isEqualTo(1.toString() + " seconds")
    }

    @Test
    fun `tick changes values to correct time`(){
        timeProvider.currentTime = 1000
        val endDate = Date(3000)


        val model = OnePerModelUIModel(endDate, timeProvider)

        timeProvider.currentTime = 2000
        model.tick()

        val endTime = "OPM= Ends at: " + formatter.format(endDate)
        Truth.assertThat(model.endsAt).isEqualTo(endTime)

        Truth.assertThat(model.finishedLabelColor).isEqualTo(R.color.red)

        Truth.assertThat(model.time).isEqualTo(1.toString() + " seconds")
    }

    @Test
    fun `tick changes to finish state when time runs out`(){
        timeProvider.currentTime = 1000
        val endDate = Date(3000)


        val model = OnePerModelUIModel(endDate, timeProvider)

        timeProvider.currentTime = 3000
        model.tick()

        val endTime = "OPM= Ends at: " + formatter.format(endDate)
        Truth.assertThat(model.endsAt).isEqualTo(endTime)

        Truth.assertThat(model.finishedLabelColor).isEqualTo(R.color.green)

        Truth.assertThat(model.time).isEqualTo("Finish")
    }
}