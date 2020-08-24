package com.sample.experiments.ui.items.timer

import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import com.sample.experiments.R
import com.sample.experiments.domain.DashboardItem
import com.sample.experiments.domain.TimeProvider
import com.sample.experiments.ui.items.BindingViewHolder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class SingleEngineTimerViewHolder(view: View) : BindingViewHolder<SingleEngineTimerUIModel>(view) {
    val titleLabel: TextView = view.findViewById(R.id.titleLabel)
    val timer: TextView = view.findViewById(R.id.timerText)
    var model: SingleEngineTimerUIModel? = null


    override fun bind(model: SingleEngineTimerUIModel) {
        this.model?.listenToTimeChanges(null)

        titleLabel.text = model.endsAt
        timer.text = model.time
        timer.setBackgroundResource(model.finishedLabelColor)
        model.listenToTimeChanges { remaining, finishedColor ->
            timer.text = remaining
            timer.setBackgroundResource(finishedColor)
        }
        this.model = model
    }
}

data class SingleEngineTimerUIModel(
    val id: Long,
    private val endDate: Date,
    private val timeProvider: TimeProvider
) : DashboardItem {
    companion object {
        private val formatter = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
    }

    private var timeChanges: ((remaining: String, finishedColor: Int) -> Unit)? = null

    val endsAt: String = "SE= Ends at: " + formatter.format(endDate)

    val finished: Boolean
        get() = getRemainingTime() <= 0

    @ColorRes
    var finishedLabelColor: Int = R.color.red


    var time: String by Delegates.observable(getRemainingTimeMessage(getRemainingTime())) { _, old, new ->
        timeChanges?.invoke(old, finishedLabelColor)
    }

    fun listenToTimeChanges(call: ((remaining: String, finishedColor: Int) -> Unit)?) {
        timeChanges = call
    }

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