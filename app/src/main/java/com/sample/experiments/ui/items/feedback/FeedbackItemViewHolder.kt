package com.sample.experiments.ui.items.feedback

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.sample.experiments.domain.FeedbackItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item2.*

class FeedbackItemViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer, FeedbackItemView {

    lateinit var presenter: FeedbackPresenter

    fun bind(item: FeedbackItem) {
        Log.e("NEW PRESENTER", item.toString())

        presenter = FeedbackPresenter(item, this)
        button2.setOnClickListener {
            onButtonClick()
        }
    }

    override fun showTitle(title: String) {
        titleLabel.text = title
    }

    override fun showButton(show: Boolean) {
        button2.isVisible = show
    }

    override fun onButtonClick() {
        presenter.onButtonClick()
    }

    override fun showMessage(message: String) {
        Toast.makeText(containerView.context, message, Toast.LENGTH_SHORT).show()
    }
}