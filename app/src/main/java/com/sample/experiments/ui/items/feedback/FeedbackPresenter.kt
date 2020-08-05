package com.sample.experiments.ui.items.feedback

import com.sample.experiments.domain.FeedbackItem
import com.sample.experiments.ui.items.BasePresenter
import javax.inject.Inject

class FeedbackPresenter @Inject constructor() : BasePresenter<FeedbackItemView>() {

    lateinit var item : FeedbackItem

    fun updateItem(item: FeedbackItem) {
        this.item = item

        view?.showButton(item.feedbackAvailable)
        view?.showTitle(item.title)
    }

    fun onButtonClick() {
        view?.showMessage("Click on: ${item.title}")
    }
}