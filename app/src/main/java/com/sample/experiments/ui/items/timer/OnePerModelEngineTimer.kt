package com.sample.experiments.ui.items.timer

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
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
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

@ModelView(
    saveViewState = true,
    autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT
)
class OnePerModelEngineTimer @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyle: Int = 0
) : RelativeLayout(context, attr, defStyle) {

    private lateinit var timerEngine: Observable<Long>
    private lateinit var masterKeyDisposable: Disposable

    private val titleLabel: TextView
    private val timer: TextView

    private lateinit var model: OnePerModelModel

    private var currentRunKey : Disposable? = null

    init {
        val root = LayoutInflater
            .from(context)
            .inflate(R.layout.item4, this, false)

        addView(root)

        titleLabel = findViewById(R.id.titleLabel)
        timer = findViewById(R.id.timerText)

        createAndStartEngine()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        //to stop the engine completely
        masterKeyDisposable.dispose()
    }

    private fun createAndStartEngine() {
        timerEngine = Observable.interval(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
            .share()
        masterKeyDisposable = timerEngine.subscribe()
    }

    @ModelProp
    fun setModel(model: OnePerModelModel) {
        this.model = model
        setBindValues()
    }

    private fun setBindValues() {
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

    @OnViewRecycled
    fun viewRecycled() {
        currentRunKey?.dispose()
    }
}

data class OnePerModelModel(
    val endDate : Date,
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