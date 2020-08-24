package com.sample.experiments.domain

import androidx.collection.ArraySet
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow
import java.util.*

interface DownloadUseCase {

    fun download(item: DownloadableItem): Flow<DownloadStatus>
    fun status(item: DownloadableItem): DownloadStatus?

    sealed class DownloadStatus {
        data class Downloading(val percent: Int) : DownloadStatus()
        object Finished : DownloadStatus()
        object Error : DownloadStatus()
    }
}

interface DownloadUseCase2 : DownloadUseCase {

    val onStatusChangeObservers: ArraySet<OnStatusChange>

    fun download2(item: DownloadableItem)

    interface OnStatusChange {
        fun item(): DownloadableItem?
        fun onStatusChange(status: DownloadUseCase.DownloadStatus)
    }
}

interface DownloadUseCase3 : DownloadUseCase {
    fun download3(item: DownloadableItem): Observable<DownloadUseCase.DownloadStatus>
}


interface GetItemsUseCase {
    operator fun invoke(): List<DashboardItem>
}


interface GetItemsUseCase2 {
    operator fun invoke(): Observable<List<DashboardItem>>
}

interface FormatDate {
    operator fun invoke(format: String, date: Date): String
}

interface TimeProvider {
    fun currentTime(): Long
}