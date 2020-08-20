package com.sample.experiments.ui.items.timer

import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import com.sample.experiments.R
import com.sample.experiments.domain.DashboardItem
import com.sample.experiments.ui.items.BindingViewHolder
import java.util.*

class CopyPerRowViewHolder(view: View) : BindingViewHolder<CopyPerRowUIModel>(view) {


    val titleLabel: TextView = view.findViewById(R.id.titleLabel)
    val timer: TextView = view.findViewById(R.id.timerText)

    override fun bind(model: CopyPerRowUIModel) {
        titleLabel.text = model.endsAt
        timer.text = model.time
        timer.setBackgroundResource(model.finishedLabelColor)
    }

}

data class CopyPerRowUIModel(
    val endDate: Date,
    val endsAt: String,
    val time: String,
    @ColorRes
    val finishedLabelColor: Int
) : DashboardItem {

    private fun getRemainingTime(endDate: Long, current: Long) =
        endDate - current

    private fun getRemainingTimeMessage(remaining: Long) =
        if (remaining > 0) {
            (remaining / 1_000).toInt().toString() + " seconds"
        } else {
            "Finish"
        }

    private fun getFinishedColor(remainingTime: Long) =
        if (remainingTime > 0) R.color.red else R.color.green

    //no need for this function and we can keep it outside
    // it's here to just help with the sample
    fun copyWithCurrentTime(current: Long): CopyPerRowUIModel {
        val remaining = getRemainingTime(endDate.time, current)
        val timeMessage = getRemainingTimeMessage(remaining)

        return copy(
            time = timeMessage,
            finishedLabelColor = getFinishedColor(remaining)
        )
    }

}