package com.sample.experiments.impl

import com.sample.experiments.domain.*
import javax.inject.Inject

class GetItemsUseCaseImpl @Inject constructor() : GetItemsUseCase {

    private val feedbackItems: List<FeedbackItem> = listOf(
        FeedbackItem("Item WithFeedback", true),
        FeedbackItem("Item Without", false),
        FeedbackItem("Item Without 2", false)
    )

    private val downloadableItems: List<DownloadableItem> = listOf(
        DownloadableItem("1", "Download 1"),
        DownloadableItem("2", "Download 2"),
        DownloadableItem("3", "Download 3"),
        DownloadableItem("4", "Download 4"),
        DownloadableItem("5", "Download 5")
    )

    private val purchaseItem: List<PurchaseItem> = listOf(
        PurchaseItem(
            "Item 1",
            "100",
            Permissions.ALL
        ),
        PurchaseItem(
            "Item 2",
            "200",
            Permissions.BID_ONLY
        ),
        PurchaseItem(
            "Item 3",
            "300",
            Permissions.PURCHASE_ONLY
        ),
        PurchaseItem(
            "Item 4",
            "10",
            Permissions.NONE
        ),
        PurchaseItem(
            "Item 5",
            "20",
            Permissions.NONE
        ),
        PurchaseItem(
            "Item 6",
            "",
            Permissions.ALL
        ),
        PurchaseItem(
            "Item 7",
            "",
            Permissions.BID_ONLY
        ),
        PurchaseItem(
            "Item 8",
            "1000",
            Permissions.PURCHASE_ONLY
        )
    )

    override fun invoke() = (purchaseItem + downloadableItems + feedbackItems).shuffled()
}