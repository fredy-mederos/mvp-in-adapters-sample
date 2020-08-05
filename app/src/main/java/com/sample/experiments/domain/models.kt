package com.sample.experiments.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PurchaseItem(
    val title: String,
    val value: String,
    val permissions: Permissions
) : DashboardItem{
    companion object{
        const val EXTRA_PURCHASE = "EXTRA_PURCHASE"
    }
}

enum class Permissions {
    PURCHASE_ONLY, BID_ONLY, NONE, ALL
}

@Parcelize
data class FeedbackItem(
    val title: String,
    val feedbackAvailable: Boolean
) : DashboardItem{

    companion object{
        const val EXTRA_FEEDBACK = "EXTRA_FEEDBACK"
    }
}

@Parcelize
data class DownloadableItem(
    val id: String,
    val title: String
) : DashboardItem {

    companion object {
        const val EXTRA_DOWNLOADABLE = "EXTRA_DOWNLOADABLE"
    }
}

interface DashboardItem : Parcelable