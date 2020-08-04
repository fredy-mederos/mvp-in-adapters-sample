package com.sample.experiments.ui.items.feedback

import com.sample.experiments.ui.items.BasePresenter

interface FeedbackItemView : BasePresenter.View {
    fun showTitle(title: String)
    fun showButton(show: Boolean)
    fun showMessage(message: String)
}