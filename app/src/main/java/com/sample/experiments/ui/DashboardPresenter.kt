package com.sample.experiments.ui

import com.sample.experiments.domain.DashboardItem
import com.sample.experiments.domain.GetItemsUseCase2
import com.sample.experiments.domain.TimeProvider
import com.sample.experiments.ui.items.BasePresenter
import com.sample.experiments.ui.items.timer.CopyPerRowUIModel
import com.sample.experiments.ui.items.timer.SingleEngineTimerUIModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DashboardPresenter @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase2,
    private val timeProvider: TimeProvider
) : BasePresenter<DashBoardView>() {


    fun loadItems() {
        compositeDisposable.add(getItemsUseCase
            .invoke()
            .publish {
                Observable.merge(
                    //pass on the first items we receive
                    it.take(1),
                    //updates on every second
                    it.updateItems()
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view?.showItems(it)
            })
    }


    private fun Observable<List<DashboardItem>>.updateItems(): Observable<List<DashboardItem>> =
        switchMap { items ->
            Observable.interval(
                1,
                TimeUnit.SECONDS,
                AndroidSchedulers.mainThread()
            ).doOnNext {
                //updating single engines without changing their objects
                // view is already subscribed to the change events
                items.forEach {
                    if (it is SingleEngineTimerUIModel) {
                        it.tick()
                    }
                }
            }
                //copy and create new UIModel object for `CopyPerRowUIModel` and send it downstream
                .scan(items, { t1, t2 ->
                    t1.map {
                        val current = timeProvider.currentTime()
                        if (it is CopyPerRowUIModel) {
                            it.copyWithCurrentTime(current)
                        } else {
                            it
                        }
                    }
                })
                .skip(1)
        }

}