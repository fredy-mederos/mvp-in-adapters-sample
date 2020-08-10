package com.sample.experiments.ui.items.timer

import android.view.View
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.sample.experiments.di.ViewHoldersEntryPoint
import com.sample.experiments.domain.TimerItem
import com.sample.experiments.ui.items.MVPViewHolder
import dagger.hilt.android.EntryPointAccessors
import kotlinx.android.synthetic.main.item4.*

class TimeItemViewHolder(view: View) :
    MVPViewHolder<TimerItemView, TimerItemPresenter, TimerItem>(view), TimerItemView {

    override val presenter: TimerItemPresenter = EntryPointAccessors.fromActivity(
        containerView.context as AppCompatActivity,
        ViewHoldersEntryPoint::class.java
    ).getTimerItemPresenter()

    override fun bindItem(item: TimerItem) {
        super.bindItem(item)
        presenter.updateItem(item)
    }

    override fun getView() = this

    override fun showTitle(title: String) {
        titleLabel.text = title
    }

    override fun showRemainingTime(remainingTime: String) {
        timerText.text = remainingTime
    }

    override fun changeRemainingTimeColor(@ColorRes colorRes: Int) {
        timerText.setBackgroundResource(colorRes)
    }

}