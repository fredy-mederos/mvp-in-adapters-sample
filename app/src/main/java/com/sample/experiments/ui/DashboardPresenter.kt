package com.sample.experiments.ui

import com.sample.experiments.R
import com.sample.experiments.domain.DashboardItem
import com.sample.experiments.domain.GetItemsUseCase
import com.sample.experiments.domain.TimeProvider
import com.sample.experiments.ui.items.BasePresenter
import com.sample.experiments.ui.items.timer.CopyPerRowUIModel
import com.sample.experiments.ui.items.timer.OnePerModelModel
import com.sample.experiments.ui.items.timer.SingleEngineTimerModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import java.text.SimpleDateFormat
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

    private val formatter = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)


//    fun loadItems() {
//        val items = getItemsUseCase()
//        view?.showItems(items)
//    }


    fun loadItems() {
//        compositeDisposable.add(Observable.interval(
//            1,
//            TimeUnit.SECONDS,
//            AndroidSchedulers.mainThread()
//        )
//            .subscribe {
//                items.forEach {
//                    if (it is SingleEngineTimerModel) {
//                        it.tick()
//                    }
//                }
//            })
        compositeDisposable.add(Observable.just(timerItems)
            .publish {
                Observable.merge(it.take(1), it.updateItems())
            }
            .subscribe {
                view?.showItems(it)
            })
    }

    private val timerItems: List<DashboardItem> by lazy {
        val baseEndTime = System.currentTimeMillis() + 30_000 //30 seconds from now
        val items = ArrayList<DashboardItem>()
        repeat(1000) {
            val date = Date(baseEndTime + Random.nextLong(90_000))
            when (Random.nextInt(1, 5)) {
                1 -> {
                    items.add(SingleEngineTimerModel(date.time, date, timeProvider))
                }
                2 -> {
                    items.add(OnePerModelModel(date, timeProvider))
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
        (remaining / 1_000).toInt().toString() + " seconds"

    private fun getFinishedColor(remainingTime : Long) =
        if (remainingTime > 0) R.color.red else R.color.green

    private fun Observable<List<DashboardItem>>.updateItems() : Observable<List<DashboardItem>> =
        switchMap { items ->
            Observable.interval(
                1,
                TimeUnit.SECONDS,
                AndroidSchedulers.mainThread()
            ).doOnNext {
                items.forEach {
                    if (it is SingleEngineTimerModel) {
                        it.tick()
                    }
                }
            }
                .scan(items, { t1, t2 ->
                    t1.map {
                        val current = timeProvider.currentTime()
                        if(it is CopyPerRowUIModel){
                            it.copyWithCurrentTime(current)
                        }else{
                            it
                        }
                    }
                })
                .skip(1)
        }

}