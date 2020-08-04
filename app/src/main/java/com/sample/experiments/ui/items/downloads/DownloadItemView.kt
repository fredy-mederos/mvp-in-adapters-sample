package com.sample.experiments.ui.items.downloads

interface DownloadItemView {
    fun showTitle(title: String)
    fun showButton(show: Boolean)
    fun showProgress(show: Boolean)
    fun setProgress(value: Int)
    fun showDoneText(show: Boolean)
}