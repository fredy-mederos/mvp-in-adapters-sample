package com.sample.experiments.ui

import com.sample.experiments.domain.DashboardItem

interface DashBoardView {
    fun showItems(items: List<DashboardItem>)
}