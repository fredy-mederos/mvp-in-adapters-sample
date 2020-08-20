package com.sample.experiments.ui.items.timer

import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import com.sample.experiments.R
import com.sample.experiments.domain.DashboardItem
import com.sample.experiments.domain.TimeProvider
import com.sample.experiments.ui.items.BindingViewHolder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class OnePerModelViewHolder(view: View) : BindingViewHolder<OnePerModelUIModel>(view) {
    private var timerEngine: Observable<Long> =
        Observable.interval(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
            .share()
    //I have to dig into the ViewHolder callback to see where is the right position to dispose this
    // for the sake of example I think we can accept it as it is
    var masterKeyDisposable: Disposable = timerEngine.subscribe()
    var currentRunKey: Disposable? = null

    val titleLabel: TextView = view.findViewById(R.id.titleLabel)
    val timer: TextView = view.findViewById(R.id.timerText)

    override fun bind(model: OnePerModelUIModel) {
        currentRunKey?.dispose()

        model.tick()
        titleLabel.text = model.endsAt
        timer.text = model.time
        timer.setBackgroundResource(model.finishedLabelColor)

        currentRunKey = timerEngine.subscribe {
            model.tick()
            titleLabel.text = model.endsAt
            timer.text = model.time
            timer.setBackgroundResource(model.finishedLabelColor)
        }
    }

}

data class OnePerModelUIModel(
    val endDate: Date,
    private val timeProvider: TimeProvider
) : DashboardItem {
    companion object {
        private val formatter = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
    }

    val endsAt: String = "OPM= Ends at: " + formatter.format(endDate)

    @ColorRes
    var finishedLabelColor: Int = R.color.red
        private set

    var time: String = getRemainingTimeMessage(getRemainingTime())
        private set

    private fun getRemainingTimeMessage(remaining: Long) =
        (remaining / 1_000).toInt().toString() + " seconds"

    private fun getRemainingTime() =
        endDate.time - timeProvider.currentTime()

    fun tick() {
        val remained = getRemainingTime()
        if (remained > 0) {
            finishedLabelColor = R.color.red
            time = getRemainingTimeMessage(remained)
        } else {
            finishedLabelColor = R.color.green
            time = "Finish"
        }
    }
}