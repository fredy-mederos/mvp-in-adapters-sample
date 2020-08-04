package com.sample.experiments.ui.items.feedback

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.sample.experiments.di.ViewHoldersEntryPoint
import com.sample.experiments.domain.FeedbackItem
import com.sample.experiments.ui.items.MVPViewHolder
import dagger.hilt.android.EntryPointAccessors
import kotlinx.android.synthetic.main.item2.*

class FeedbackItemViewHolder(view: View) :
    MVPViewHolder<FeedbackItemView, FeedbackPresenter, FeedbackItem>(view), FeedbackItemView {

    override val presenter: FeedbackPresenter = EntryPointAccessors.fromActivity(
        containerView.context as AppCompatActivity,
        ViewHoldersEntryPoint::class.java
    ).getFeedbackPresenter()

    override fun bindItem(item: FeedbackItem) {
        super.bindItem(item)
        presenter.updateItem(item)
        button2.setOnClickListener {
            presenter.onButtonClick(item)
        }
    }

    override fun showTitle(title: String) {
        titleLabel.text = title
    }

    override fun showButton(show: Boolean) {
        button2.isVisible = show
    }

    override fun showMessage(message: String) {
        Toast.makeText(containerView.context, message, Toast.LENGTH_SHORT).show()
    }

    override fun getView() = this
}