package com.sample.experiments.ui.items.feedback

import com.sample.experiments.domain.FeedbackItem

class FeedbackPresenter(private val item: FeedbackItem, private val view: FeedbackItemView) {

    init {
        view.showButton(item.feedbackAvailable)
        view.showTitle(item.title)
    }

    fun onButtonClick() {
        view.showMessage("Click on: ${item.title}")
    }
}