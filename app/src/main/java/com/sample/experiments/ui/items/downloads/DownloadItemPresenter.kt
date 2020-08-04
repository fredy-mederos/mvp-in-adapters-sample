package com.sample.experiments.ui.items.downloads

import com.sample.experiments.domain.DownloadUseCase
import com.sample.experiments.domain.DownloadableItem
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class DownloadItemPresenter @Inject constructor(
    private val downloadUseCase: DownloadUseCase
) : CoroutineScope by MainScope() {

    lateinit var view: DownloadItemView

    fun updateItem(item: DownloadableItem) {

        view.showTitle(item.title)
        val status = downloadUseCase.status(item)

        if (status is DownloadUseCase.DownloadStatus)
            resumeDownloading(item)
        else
            updateViewWithStatus(downloadUseCase.status(item))
    }

    fun clear() {
        coroutineContext.cancelChildren()
    }

    private fun resumeDownloading(item: DownloadableItem) {
        launch {
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