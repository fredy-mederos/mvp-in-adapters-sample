package com.sample.experiments.ui.items.timer

import com.sample.experiments.R
import com.sample.experiments.di.MainThreadScheduler
import com.sample.experiments.domain.FormatDate
import com.sample.experiments.domain.TimeProvider
import com.sample.experiments.domain.TimerItem
import com.sample.experiments.ui.items.BasePresenter
import io.reactivex.Observable
import io.reactivex.Scheduler
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TimerItemPresenter @Inject constructor(
    private val formatDate: FormatDate,
    private val timeProvider: TimeProvider,
    @MainThreadScheduler private val mainThreadScheduler: Scheduler
) : BasePresenter<TimerItemView>() {


    fun updateItem(item: TimerItem) {
        //DateFormat.format("HH:mm:ss", item.endDate).toString()
        view?.showTitle("Ends at: " + formatDate("HH:mm:ss", item.endDate))
        launchTimer(item)
    }

    private fun launchTimer(item: TimerItem) {
        if (item.endDate.time > timeProvider.currentTime()) {
            view?.changeRemainingTimeColor(R.color.red)
            onTimeTick(item)
            compositeDisposable.add(
                Observable.interval(1, TimeUnit.SECONDS, mainThreadScheduler)
                    .subscribe {
                        onTimeTick(item)
                    }
            )
        } else {
            onTimeEnds()
        }
    }

    private fun onTimeTick(item: TimerItem) {
        val remainingTime: Long = item.endDate.time - timeProvider.currentTime()
        if (remainingTime > 0) {
            view?.showRemainingTime(
                (remainingTime / 1_000).toInt().toString() + " seconds"
            )
        } else {
            onTimeEnds()
        }
    }

    private fun onTimeEnds() {
        compositeDisposable.clear()
        view?.changeRemainingTimeColor(R.color.green)
        view?.showRemainingTime("Finish")
    }
}