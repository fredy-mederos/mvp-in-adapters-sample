package com.sample.experiments.ui.items.downloads

import com.sample.experiments.ui.items.BasePresenter

interface DownloadItemView : BasePresenter.View{
    fun showTitle(title: String)
    fun showButton(show: Boolean)
    fun showProgress(show: Boolean)
    fun setProgress(value: Int)
    fun showDoneText(show: Boolean)
}