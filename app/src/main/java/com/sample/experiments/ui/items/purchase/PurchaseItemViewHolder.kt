package com.sample.experiments.ui.items.purchase

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.sample.experiments.di.ViewHoldersEntryPoint
import com.sample.experiments.domain.PurchaseItem
import dagger.hilt.android.EntryPointAccessors
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item.*

class PurchaseItemViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer, PurchaseItemView {

    val presenter: PurchaseItemPresenter = EntryPointAccessors.fromActivity(
        containerView.context as AppCompatActivity,
        ViewHoldersEntryPoint::class.java
    ).getPurchaseItemPresenter().apply { view = this@PurchaseItemViewHolder }

    fun bind(item: PurchaseItem) {
        //Log.e("NEW PRESENTER", item.toString())
        presenter.updateItem(item)

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
}