package com.sample.experiments.ui.items.purchase

import com.sample.experiments.ui.items.BasePresenter


interface PurchaseItemView : BasePresenter.View{
    fun showTitle(title: String)
    fun setInputValue(value: String)
    fun showInput(show: Boolean)
    fun showPurchaseButton(show: Boolean)
    fun showBidButton(show: Boolean)
    fun showMessage(message: String)
}