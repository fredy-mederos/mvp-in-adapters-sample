package com.sample.experiments.ui.items.feedback

interface FeedbackItemView {
    fun showTitle(title: String)
    fun showButton(show: Boolean)
    fun onButtonClick()
    fun showMessage(message: String)
}