package com.sample.experiments.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sample.experiments.R
import com.sample.experiments.domain.DashboardItem
import com.sample.experiments.domain.DownloadUseCase
import com.sample.experiments.ui.items.DashboardItemsAdapter
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
        recyclerView.adapter = DashboardItemsAdapter(items)
    }
}

