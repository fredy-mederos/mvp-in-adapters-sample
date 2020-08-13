package com.sample.experiments.ui

import com.sample.experiments.domain.DashboardItem
import com.sample.experiments.domain.GetItemsUseCase
import com.sample.experiments.domain.TimeProvider
import com.sample.experiments.ui.items.BasePresenter
import com.sample.experiments.ui.items.timer.OnePerModelModel
import com.sample.experiments.ui.items.timer.SingleEngineTimerModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.random.Random


class DashboardPresenter
@Inject constructor(
    private val getItemsUseCase: GetItemsUseCase,
    private val timeProvider: TimeProvider
) : BasePresenter<DashBoardView>() {


    fun loadItems() {
        val items = getItemsUseCase()
        view?.showItems(items)
    }


    fun getNewItems(): List<DashboardItem> {
        val items = timerItems
        compositeDisposable.add(Observable.interval(
            1,
            TimeUnit.SECONDS,
            AndroidSchedulers.mainThread()
        )
            .subscribe {
                items.forEach {
                    if (it is SingleEngineTimerModel) {
                        it.tick()
                    }
                }
            })
        return items
    }

    private val timerItems: List<DashboardItem> by lazy {
        val baseEndTime = System.currentTimeMillis() + 30_000 //30 seconds from now
        val items = ArrayList<DashboardItem>()
        repeat(1000) {
            val date = Date(baseEndTime + Random.nextLong(90_000))
            when (Random.nextInt(1, 3)) {
                1 -> {
                    items.add(SingleEngineTimerModel(date.time, date, timeProvider))
                }
                2 -> {
                    items.add(OnePerModelModel(date, timeProvider))
                }
            }

        }
        items
    }
}