package com.sample.experiments.ui.items.purchase

import com.sample.experiments.domain.Permissions
import com.sample.experiments.domain.PurchaseItem
import com.sample.experiments.ui.items.BasePresenter
import javax.inject.Inject

class PurchaseItemPresenter @Inject constructor() : BasePresenter<PurchaseItemView>() {

    fun updateItem(item: PurchaseItem) {
        view?.setInputValue(item.value)
        view?.showTitle(item.title)

        view?.showPurchaseButton(item.permissions == Permissions.PURCHASE_ONLY || item.permissions == Permissions.ALL)
        view?.showBidButton(item.permissions == Permissions.BID_ONLY || item.permissions == Permissions.ALL)
        view?.showInput(item.permissions == Permissions.ALL)
    }

    fun onPurchaseButtonClick(item: PurchaseItem) {
        view?.showMessage("Buy click: ${item.title}")
    }

    fun onBidButtonClick(item: PurchaseItem) {
        view?.showMessage("Bid click: ${item.title}")
    }
}