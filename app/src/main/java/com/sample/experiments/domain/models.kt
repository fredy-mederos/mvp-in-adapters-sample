package com.sample.experiments.domain

import java.util.*

data class PurchaseItem(
    val title: String,
    val value: String,
    val permissions: Permissions
) : DashboardItem

enum class Permissions {
    PURCHASE_ONLY, BID_ONLY, NONE, ALL
}

data class FeedbackItem(
    val title: String,
    val feedbackAvailable: Boolean
) : DashboardItem

data class DownloadableItem(
    val id: String,
    val title: String
) : DashboardItem

data class TimerItem(
    val title: String,
    val endDate: Date
) : DashboardItem

interface DashboardItem