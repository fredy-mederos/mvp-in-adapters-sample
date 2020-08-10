package com.sample.experiments.ui.items.timer

import androidx.annotation.ColorRes
import com.sample.experiments.ui.items.BasePresenter

interface TimerItemView : BasePresenter.View {
    fun showTitle(title: String)
    fun showRemainingTime(remainingTime: String)
    fun changeRemainingTimeColor(@ColorRes colorRes: Int)
}