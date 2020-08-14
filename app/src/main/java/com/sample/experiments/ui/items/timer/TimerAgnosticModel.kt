package com.sample.experiments.ui.items.timer

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.OnViewRecycled
import com.sample.experiments.R
import com.sample.experiments.domain.DashboardItem
import com.sample.experiments.domain.TimeProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@ModelView(
    saveViewState = true,
    autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT
)
class TimerAgnosticModel @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyle: Int = 0
) : RelativeLayout(context, attr, defStyle) {

    private val titleLabel: TextView
    private val timer: TextView

    private lateinit var model: CopyPerRowUIModel

    init {
        val root = LayoutInflater
            .from(context)
            .inflate(R.layout.item4, this, false)

        addView(root)

        titleLabel = findViewById(R.id.titleLabel)
        timer = findViewById(R.id.timerText)
    }

    @ModelProp
    fun setModel(model: CopyPerRowUIModel) {
        this.model = model
        setBindValues()
    }

    private fun setBindValues() {
        titleLabel.text = model.endsAt
        timer.text = model.time
        timer.setBackgroundResource(model.finishedLabelColor)
    }
}

data class CopyPerRowUIModel(
    val endDate : Date,
    val endsAt : String,
    val time : String,
    @ColorRes
    val finishedLabelColor: Int
) : DashboardItem {

//    val endsAt: String = "OPM= Ends at: " + formatter.format(endDate)

//    @ColorRes
//    var finishedLabelColor: Int = R.color.red
//        private set

//    var time: String = getRemainingTimeMessage(getRemainingTime())
//        private set



    private fun getRemainingTime(endDate : Long, current : Long) =
        endDate - current

    private fun getRemainingTimeMessage(remaining: Long) =
        (remaining / 1_000).toInt().toString() + " seconds"

    private fun getFinishedColor(remainingTime : Long) =
        if (remainingTime > 0) R.color.red else R.color.green

    fun copyWithCurrentTime(current : Long) : CopyPerRowUIModel {
        val remaining = getRemainingTime(endDate.time, current)
        val timeMessage = getRemainingTimeMessage(remaining)

        return copy(
            time = timeMessage,
            finishedLabelColor = if (remaining > 0) R.color.red else R.color.green
        )
    }

}