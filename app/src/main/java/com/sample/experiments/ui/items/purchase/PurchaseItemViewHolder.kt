package com.sample.experiments.ui.items.purchase

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.sample.experiments.domain.PurchaseItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item.*

class PurchaseItemViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer, PurchaseItemView {

    lateinit var presenter: PurchaseItemPresenter

    fun bind(item: PurchaseItem) {
        Log.e("NEW PRESENTER", item.toString())
        presenter = PurchaseItemPresenter(item, this)

        button1.setOnClickListener {
            onBidButtonClick()
        }
        button2.setOnClickListener {
            onPurchaseButtonClick()
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

    override fun onPurchaseButtonClick() {
        presenter.onPurchaseButtonClick()
    }

    override fun onBidButtonClick() {
        presenter.onBidButtonClick()
    }

    override fun showMessage(message: String) {
        Toast.makeText(containerView.context, message, Toast.LENGTH_SHORT).show()
    }
}