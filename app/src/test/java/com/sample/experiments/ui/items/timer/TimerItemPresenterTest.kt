package com.sample.experiments.ui.items.timer

import com.sample.experiments.R
import com.sample.experiments.domain.FormatDate
import com.sample.experiments.domain.TimeProvider
import com.sample.experiments.domain.TimerItem
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import io.reactivex.schedulers.TestScheduler
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*
import java.util.Calendar.SECOND
import java.util.concurrent.TimeUnit

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
internal class TimerItemPresenterTest {
    @RelaxedMockK
    lateinit var view: TimerItemView

    lateinit var testScheduler: TestScheduler
    lateinit var timeProvider: TimeProviderTestImpl

    @BeforeEach
    fun setUp() {
        testScheduler = TestScheduler()
        timeProvider = TimeProviderTestImpl()
    }

    @Test
    fun `test the whole flow`() {
        val presenter = TimerItemPresenter(FormatDateTestImpl("10:20"), timeProvider, testScheduler)
        presenter.view = view

        val now = Calendar.getInstance()
        timeProvider.currentTime = now.timeInMillis
        val endTime = now.apply { add(SECOND, 5) }.time

        val item = TimerItem(endTime)
        presenter.updateItem(item)
        verify(exactly = 1) { view.showTitle("Ends at: 10:20") }
        verify(exactly = 1) { view.changeRemainingTimeColor(R.color.red) }

        verify(exactly = 1) { view.showRemainingTime("5 seconds") }
        advance1Second()
        verify(exactly = 1) { view.showRemainingTime("4 seconds") }
        advance1Second()
        verify(exactly = 1) { view.showRemainingTime("3 seconds") }
        advance1Second()
        verify(exactly = 1) { view.showRemainingTime("2 seconds") }
        advance1Second()
        verify(exactly = 1) { view.showRemainingTime("1 seconds") }
        advance1Second()
        verify(exactly = 1) { view.showRemainingTime("Finish") }
        verify(exactly = 1) { view.changeRemainingTimeColor(R.color.green) }
    }

    private fun advance1Second() {
        timeProvider.advanceTimeBy(1_000)
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
    }

}

class TimeProviderTestImpl : TimeProvider {
    var currentTime: Long = 0

    fun advanceTimeBy(timeInMillis: Long) {
        currentTime += timeInMillis
    }

    override fun currentTime() = currentTime

}

class FormatDateTestImpl(var valueToReturn: String) : FormatDate {
    override fun invoke(format: String, date: Date) = valueToReturn
}