package com.sample.experiments.ui.items.timer

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.OnViewRecycled
import com.airbnb.epoxy.SimpleEpoxyModel
import com.sample.experiments.R
import com.sample.experiments.domain.DashboardItem
import com.sample.experiments.domain.TimeProvider
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

@ModelView(
    saveViewState = true,
    autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT
)
class TimerEpoxyModel @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyle: Int = 0
) : RelativeLayout(context, attr, defStyle) {

    private val titleLabel: TextView
    private val timer: TextView

    private lateinit var model: SingleEngineTimerModel

    init {
        val root = LayoutInflater
            .from(context)
            .inflate(R.layout.item4, this, false)

        addView(root)

        titleLabel = findViewById(R.id.titleLabel)
        timer = findViewById(R.id.timerText)
    }


    @ModelProp
    fun setModel(model: SingleEngineTimerModel) {
        this.model = model
        titleLabel.text = model.endsAt
        timer.text = model.time
        timer.setBackgroundResource(model.finishedLabelColor)
        Log.d("asghar" + model.id, "start=" + System.currentTimeMillis())
        Log.d("asghar" + model.id, "start modelTime=" + model.time)
        model.listenToTimeChanges { remaining, finishedColor ->
            Log.d("asghar" + model.id, "finish=" + System.currentTimeMillis())
            Log.d("asghar" + model.id, "finish modelTime=" + model.time)
            timer.text = remaining
            timer.setBackgroundResource(finishedColor)
        }
    }

    @OnViewRecycled
    fun viewRecycled() {
        model.listenToTimeChanges(null)
    }

}

data class SingleEngineTimerModel(
    val id: Long,
    private val endDate: Date,
    private val timeProvider: TimeProvider
): DashboardItem {
    companion object {
        private val formatter = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
    }

    private var timeChanges: ((remaining: String, finishedColor: Int) -> Unit)? = null

    val endsAt: String = "Ends at: " + formatter.format(endDate)

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