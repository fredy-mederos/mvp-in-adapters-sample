package com.sample.experiments.ui.items.purchase

import com.sample.experiments.domain.Permissions
import com.sample.experiments.domain.PurchaseItem

class PurchaseItemPresenter(private val item: PurchaseItem, val view: PurchaseItemView) {
    init {
        view.setInputValue(item.value)
        view.showTitle(item.title)

        view.showPurchaseButton(item.permissions == Permissions.PURCHASE_ONLY || item.permissions == Permissions.ALL)
        view.showBidButton(item.permissions == Permissions.BID_ONLY || item.permissions == Permissions.ALL)
        view.showInput(item.permissions == Permissions.ALL)
    }

    fun onPurchaseButtonClick() {
        view.showMessage("Buy click: ${item.title}")
    }

    fun onBidButtonClick() {
        view.showMessage("Bid click: ${item.title}")
    }
}