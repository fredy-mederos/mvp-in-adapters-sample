package com.sample.experiments.ui.items.timer

import com.google.common.truth.Truth.assertThat
import com.sample.experiments.R
import org.junit.jupiter.api.Test
import java.util.*

class CopyPerRowUIModelTest {

    @Test
    fun `shows correct data for an endDate`() {
        val endDate = Date(1)
        val endsAt = "TAM= Ends at: ${endDate.time}"
        val time = "5:00"

        val model = CopyPerRowUIModel(endDate, endsAt, time, R.color.red)

        assertThat(model.endsAt).isEqualTo(endsAt)
        assertThat(model.finishedLabelColor).isEqualTo(R.color.red)
        assertThat(model.time).isEqualTo(time)
    }


    @Test
    fun `shows correct data after copy`() {
        val endDate = Date(2000)
        val endsAt = "TAM= Ends at: ${endDate.time}"
        val time = "5:00"

        val model = CopyPerRowUIModel(endDate, endsAt, time, R.color.red)
            .copyWithCurrentTime(1000)

        assertThat(model.endsAt).isEqualTo(endsAt)
        assertThat(model.finishedLabelColor).isEqualTo(R.color.red)
        assertThat(model.time).isEqualTo(1.toString() + " seconds")
    }


    @Test
    fun `shows finished state when time runs out`() {
        val endDate = Date(2000)
        val endsAt = "TAM= Ends at: ${endDate.time}"
        val time = "5:00"

        val model = CopyPerRowUIModel(endDate, endsAt, time, R.color.red)
            .copyWithCurrentTime(2000)

        assertThat(model.endsAt).isEqualTo(endsAt)
        assertThat(model.finishedLabelColor).isEqualTo(R.color.green)
        assertThat(model.time.toLowerCase()).isEqualTo("finish")
    }
}
