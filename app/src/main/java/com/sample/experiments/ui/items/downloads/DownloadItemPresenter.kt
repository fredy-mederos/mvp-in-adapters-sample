package com.sample.experiments.ui.items.downloads

import com.sample.experiments.domain.DownloadUseCase
import com.sample.experiments.domain.DownloadableItem
import com.sample.experiments.ui.items.BasePresenter
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

class DownloadItemPresenter @Inject constructor(
    private val downloadUseCase: DownloadUseCase
) : BasePresenter<DownloadItemView>() {

    fun updateItem(item: DownloadableItem) {

        view?.showTitle(item.title)
        val status = downloadUseCase.status(item)

        if (status is DownloadUseCase.DownloadStatus)
            resumeDownloading(item)
        else
            updateViewWithStatus(downloadUseCase.status(item))
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
        view?.showButton(status == null)
        view?.showProgress(status is DownloadUseCase.DownloadStatus.Downloading)
        view?.showDoneText(status is DownloadUseCase.DownloadStatus.Finished)

        if (status is DownloadUseCase.DownloadStatus.Downloading) {
            view?.setProgress(status.percent)
        }
    }
}