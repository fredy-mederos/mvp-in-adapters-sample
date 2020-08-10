package com.sample.experiments.ui

import com.sample.experiments.domain.DashboardItem
import com.sample.experiments.ui.items.BasePresenter

interface DashBoardView : BasePresenter.View {
    fun showItems(items: List<DashboardItem>)
}