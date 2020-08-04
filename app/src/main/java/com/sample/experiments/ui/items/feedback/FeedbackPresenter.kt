package com.sample.experiments.ui.items.feedback

import com.sample.experiments.domain.FeedbackItem
import com.sample.experiments.ui.items.BasePresenter
import javax.inject.Inject

class FeedbackPresenter @Inject constructor() : BasePresenter<FeedbackItemView>() {

    fun updateItem(item: FeedbackItem) {
        view?.showButton(item.feedbackAvailable)
        view?.showTitle(item.title)
    }

    fun onButtonClick(item: FeedbackItem) {
        view?.showMessage("Click on: ${item.title}")
    }
}