package com.sample.experiments.ui.items.purchase

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.sample.experiments.di.ViewHoldersEntryPoint
import com.sample.experiments.domain.PurchaseItem
import com.sample.experiments.ui.items.MVPViewHolder
import dagger.hilt.android.EntryPointAccessors
import kotlinx.android.synthetic.main.item.*

class PurchaseItemViewHolder(view: View) : MVPViewHolder<PurchaseItemView, PurchaseItemPresenter, PurchaseItem>(view), PurchaseItemView {

    override val presenter: PurchaseItemPresenter = EntryPointAccessors.fromActivity(
        containerView.context as AppCompatActivity,
        ViewHoldersEntryPoint::class.java
    ).getPurchaseItemPresenter()

    override fun bindItem(item: PurchaseItem) {
        super.bindItem(item)

        button1.setOnClickListener {
            presenter.onBidButtonClick(item)
        }
        button2.setOnClickListener {
            presenter.onPurchaseButtonClick(item)
        }
    }

    override fun showTitle(title: String) {
        titleLabel.text = title
    }

    override fun setInputValue(value: String) {
        editText.setText(value)
    }

    override fun showInput(show: Boolean) {
        editText.isVisible = show
    }

    override fun showPurchaseButton(show: Boolean) {
        button2.isVisible = show
    }

    override fun showBidButton(show: Boolean) {
        button1.isVisible = show
    }

    override fun showMessage(message: String) {
        Toast.makeText(containerView.context, message, Toast.LENGTH_SHORT).show()
    }

    override fun getView() = this
}