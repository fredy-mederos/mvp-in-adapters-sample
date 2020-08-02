package com.sample.experiments.ui.items.downloads

import com.sample.experiments.domain.DownloadUseCase
import com.sample.experiments.domain.DownloadableItem
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class DownloadItemPresenter(
    private val item: DownloadableItem,
    private val view: DownloadItemView,
    private val downloadUseCase: DownloadUseCase
) : CoroutineScope by MainScope() {

    init {
        view.showTitle(item.title)
        val status = downloadUseCase.status(item)

        if (status is DownloadUseCase.DownloadStatus)
            resumeDownloading()
        else
            updateViewWithStatus(downloadUseCase.status(item))
    }

    private fun resumeDownloading() {
        launch {
            downloadUseCase.download(item).collect { status ->
                if (isActive) {
                    updateViewWithStatus(status)
                }
            }
        }
    }

    fun onDownloadItemClick() {
        resumeDownloading()
    }

    fun clear() {
        coroutineContext.cancelChildren()
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