package com.sample.experiments.ui

import com.sample.experiments.domain.GetItemsUseCase
import com.sample.experiments.domain.TimeProvider
import com.sample.experiments.ui.items.BasePresenter
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


    fun getNewItems() : List<SingleEngineTimerModel>{
        val items = timerItems
        compositeDisposable.add(Observable.interval(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
            .subscribe {
                items.forEach {
                    it.tick()
                }
            })
        return items
    }

    private val timerItems: List<SingleEngineTimerModel> by lazy {
        val baseEndTime = System.currentTimeMillis() + 30_000 //30 seconds from now
        val items = ArrayList<SingleEngineTimerModel>()
        repeat(1000) {
            val date = Date(baseEndTime + Random.nextLong(90_000))
            items.add(SingleEngineTimerModel(date.time, date, timeProvider))
        }
        items
    }
}