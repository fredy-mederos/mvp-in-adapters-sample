package com.sample.experiments.ui.items.downloads

import com.sample.experiments.domain.DownloadUseCase
import com.sample.experiments.domain.DownloadUseCase2
import com.sample.experiments.domain.DownloadableItem
import com.sample.experiments.ui.items.BasePresenter
import javax.inject.Inject

class DownloadItemPresenter2 @Inject constructor(
    private val downloadUseCase: DownloadUseCase2
) : BasePresenter<DownloadItemView>(), DownloadUseCase2.OnStatusChange {

    var _item: DownloadableItem? = null
    override fun item() = _item

    fun updateItem(item: DownloadableItem) {
        this._item = item
        downloadUseCase.onStatusChangeObservers += this

        view?.showTitle(item.title)
        val status = downloadUseCase.status(item)

        if (status is DownloadUseCase.DownloadStatus.Downloading)
            resumeDownloading(item)

        updateViewWithStatus(status)
    }

    override fun onDestroy() {
        super.onDestroy()
        downloadUseCase.onStatusChangeObservers -= this
        _item = null
    }


    private fun resumeDownloading(item: DownloadableItem) {
        downloadUseCase.download2(item)
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


    override fun onStatusChange(status: DownloadUseCase.DownloadStatus) {
        updateViewWithStatus(status)
    }
}