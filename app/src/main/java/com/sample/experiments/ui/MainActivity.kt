package com.sample.experiments.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sample.experiments.R
import com.sample.experiments.domain.DashboardItem
import com.sample.experiments.domain.DownloadUseCase
import com.sample.experiments.ui.items.DashboardItemsAdapter
import com.sample.experiments.ui.items.timer.OnePerModelModel
import com.sample.experiments.ui.items.timer.SingleEngineTimerModel
import com.sample.experiments.ui.items.timer.onePerModelEngineTimer
import com.sample.experiments.ui.items.timer.timerEpoxyModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), DashBoardView {

    @Inject
    lateinit var presenter: DashboardPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.view = this

        val items = presenter.getNewItems()

        recyclerView.withModels {
            items.forEach {
                if(it is OnePerModelModel){
                    onePerModelEngineTimer {
                        id(it.endDate.time)
                        model(it)
                    }
                }else if (it is SingleEngineTimerModel){
                    timerEpoxyModel {
                        id(it.id)
                        model(it)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.view = null
    }

    override fun onResume() {
        super.onResume()
        presenter.loadItems()
    }

    override fun showItems(items: List<DashboardItem>) {

    }
}

