package com.sample.experiments.impl

import com.sample.experiments.R
import com.sample.experiments.domain.DashboardItem
import com.sample.experiments.domain.GetItemsUseCase2
import com.sample.experiments.domain.TimeProvider
import com.sample.experiments.ui.items.timer.CopyPerRowUIModel
import com.sample.experiments.ui.items.timer.OnePerModelUIModel
import com.sample.experiments.ui.items.timer.SingleEngineTimerUIModel
import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.random.Random

class GetItemUseCase2Impl @Inject constructor(
    private val timeProvider: TimeProvider
) : GetItemsUseCase2 {
    private val formatter = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)

    override fun invoke(): Observable<List<DashboardItem>> =
        Observable.just(timerItems)
            .delay(2, TimeUnit.SECONDS)


    private val timerItems: List<DashboardItem> by lazy {
        val baseEndTime = System.currentTimeMillis() + 30_000 //30 seconds from now
        val items = ArrayList<DashboardItem>()
        repeat(1000) {
            val date = Date(baseEndTime + Random.nextLong(90_000))
            when (Random.nextInt(1, 5)) {
                1 -> {
                    items.add(SingleEngineTimerUIModel(date.time, date, timeProvider))
                }
                2 -> {
                    items.add(OnePerModelUIModel(date, timeProvider))
                }
                3 -> {
                    val remaining = getRemainingTime(date.time, timeProvider.currentTime())
                    items.add(
                        CopyPerRowUIModel(
                            date,
                            "TAM= Ends at: " + formatter.format(date),
                            getRemainingTimeMessage(remaining),
                            getFinishedColor(remaining)
                        )
                    )
                }
            }

        }
        items
    }

    private fun getRemainingTime(endDate: Long, current: Long) =
        endDate - current

    private fun getRemainingTimeMessage(remaining: Long) =
        if (remaining > 0) {
            (remaining / 1_000).toInt().toString() + " seconds"
        } else {
            "Finish"
        }

    private fun getFinishedColor(remainingTime : Long) =
        if (remainingTime > 0) R.color.red else R.color.green
}