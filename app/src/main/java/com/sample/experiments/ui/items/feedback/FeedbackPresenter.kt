package com.sample.experiments.ui.items.feedback

import com.sample.experiments.domain.FeedbackItem
import javax.inject.Inject

class FeedbackPresenter @Inject constructor() {

    lateinit var view: FeedbackItemView

    fun updateItem(item: FeedbackItem) {
        view.showButton(item.feedbackAvailable)
        view.showTitle(item.title)
    }

    fun onButtonClick(item: FeedbackItem) {
        view.showMessage("Click on: ${item.title}")
    }
}