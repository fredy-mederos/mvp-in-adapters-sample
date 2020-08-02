package com.sample.experiments.ui

import com.sample.experiments.domain.GetItemsUseCase
import javax.inject.Inject


class DashboardPresenter @Inject constructor(private val getItemsUseCase: GetItemsUseCase) {
    var view: DashBoardView? = null

    fun loadItems() {
        val items = getItemsUseCase()
        view?.showItems(items)
    }
}