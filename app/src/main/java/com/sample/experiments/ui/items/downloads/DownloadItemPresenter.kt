package com.sample.experiments.ui.items.downloads

import com.sample.experiments.domain.DownloadUseCase
import com.sample.experiments.domain.DownloadableItem
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class DownloadItemPresenter(
    private val view: DownloadItemView,
    private val downloadUseCase: DownloadUseCase
) : CoroutineScope by MainScope() {

    var job:Job?=null

    fun updateItem(item: DownloadableItem) {
        job?.cancel()


        view.showTitle(item.title)
        val status = downloadUseCase.status(item)

        if (status is DownloadUseCase.DownloadStatus)
            resumeDownloading(item)
        else
            updateViewWithStatus(downloadUseCase.status(item))
    }

    private fun resumeDownloading(item: DownloadableItem) {
        job = launch {
            downloadUseCase.download(item).collect { status ->
                if (isActive) {
                    updateViewWithStatus(status)
                }
            }
        }
    }

    fun onDownloadItemClick(item: DownloadableItem) {
        resumeDownloading(item)
    }

    private fun updateViewWithStatus(status: DownloadUseCase.DownloadStatus?) {
        view.showButton(status == null)
        view.showProgress(status is DownloadUseCase.DownloadStatus.Downloading)
        view.showDoneText(status is DownloadUseCase.DownloadStatus.Finished)

        if (status is DownloadUseCase.DownloadStatus.Downloading) {
            view.setProgress(status.percent)
        }
    }
}