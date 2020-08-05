package com.sample.experiments.ui.items.timer

import com.sample.experiments.R
import com.sample.experiments.domain.FormatDate
import com.sample.experiments.domain.TimerItem
import com.sample.experiments.ui.items.BasePresenter
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TimerItemPresenter @Inject constructor(val formatDate: FormatDate) :
    BasePresenter<TimerItemView>() {


    fun updateItem(item: TimerItem) {
        //DateFormat.format("HH:mm:ss", item.endDate).toString()
        view?.showTitle("Ends at: " + formatDate("HH:mm:ss", item.endDate))
        launchTimer(item)
    }

    private fun launchTimer(item: TimerItem) {
        if (item.endDate.time > System.currentTimeMillis()) {
            view?.changeRemainingTimeColor(R.color.red)
            onTimeTick(item)
            compositeDisposable.add(
                Flowable.interval(1, TimeUnit.SECONDS)
                    .publish()
                    .autoConnect()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        onTimeTick(item)
                    }
            )
        } else {
            onTimeEnds()
        }
    }

    private fun onTimeTick(item: TimerItem) {
        val remainingTime: Long = item.endDate.time - System.currentTimeMillis()
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