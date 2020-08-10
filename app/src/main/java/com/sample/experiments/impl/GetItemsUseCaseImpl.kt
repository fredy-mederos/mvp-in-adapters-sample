package com.sample.experiments.impl

import com.sample.experiments.domain.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.random.Random

class GetItemsUseCaseImpl @Inject constructor() : GetItemsUseCase {

    private val feedbackItems: List<FeedbackItem> = listOf(
        FeedbackItem("Item WithFeedback", true),
        FeedbackItem("Item WithFeedback2", true),
        FeedbackItem("Item WithFeedback3", true),
        FeedbackItem("Item Without", false),
        FeedbackItem("Item Without 2", false)
    )

    private val downloadableItems: List<DownloadableItem> = listOf(
        DownloadableItem("1", "Download 1"),
        DownloadableItem("2", "Download 2"),
        DownloadableItem("3", "Download 3"),
        DownloadableItem("4", "Download 4"),
        DownloadableItem("5", "Download 5"),
        DownloadableItem("6", "Download 6"),
        DownloadableItem("7", "Download 7"),
        DownloadableItem("8", "Download 8"),
        DownloadableItem("9", "Download 9"),
        DownloadableItem("10", "Download 10"),
        DownloadableItem("11", "Download 11"),
        DownloadableItem("12", "Download 12"),
        DownloadableItem("13", "Download 13"),
        DownloadableItem("14", "Download 14"),
        DownloadableItem("15", "Download 15"),
        DownloadableItem("16", "Download 16"),
        DownloadableItem("17", "Download 17")
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


    @OptIn(ExperimentalStdlibApi::class)
    val timerItems: List<TimerItem> by lazy {
        val baseEndTime = System.currentTimeMillis() + 30_000 //30 seconds from now
        val items = ArrayList<TimerItem>()
        repeat(10) {
            val date = Date(baseEndTime + Random.nextLong(90_000))
            items.add(TimerItem(date))
        }
        items
    }

    override fun invoke() =
        (purchaseItem + downloadableItems + feedbackItems + timerItems)
            .shuffled()
}