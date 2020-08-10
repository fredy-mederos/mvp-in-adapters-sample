package com.sample.experiments.ui

import com.sample.experiments.domain.GetItemsUseCase
import com.sample.experiments.ui.items.BasePresenter
import javax.inject.Inject


class DashboardPresenter
@Inject constructor(private val getItemsUseCase: GetItemsUseCase) : BasePresenter<DashBoardView>() {

    fun loadItems() {
        val items = getItemsUseCase()
        view?.showItems(items)
    }
}