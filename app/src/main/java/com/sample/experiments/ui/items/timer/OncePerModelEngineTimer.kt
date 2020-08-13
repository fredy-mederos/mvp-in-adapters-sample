package com.sample.experiments.ui.items.timer

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.OnViewRecycled
import com.airbnb.epoxy.SimpleEpoxyModel
import com.sample.experiments.R

class OncePerModelEngineTimer(
) : SimpleEpoxyModel(R.layout.item4) {

    private lateinit var titleLabel: TextView
    private lateinit var timer: TextView

    private lateinit var model: SingleEngineTimerModel

    override fun bind(view: View) {
        super.bind(view)

        titleLabel = view.findViewById(R.id.titleLabel)
        timer = view.findViewById(R.id.timerText)

        bindToTimer()
    }

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