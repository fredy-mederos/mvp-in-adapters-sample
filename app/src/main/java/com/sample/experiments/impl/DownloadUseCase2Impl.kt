package com.sample.experiments.impl

import android.util.Log
import androidx.collection.ArraySet
import com.sample.experiments.domain.DownloadUseCase
import com.sample.experiments.domain.DownloadUseCase2
import com.sample.experiments.domain.DownloadableItem
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlin.math.min
import kotlin.random.Random


class DownloadUseCase2Impl(private val coroutineScope: CoroutineScope) :
    DownloadUseCase2 {

    private val cache: HashMap<String, DownloadUseCase.DownloadStatus> = HashMap()

    private var job: Job? = null
    private val random = Random
    override val onStatusChangeObservers = ArraySet<DownloadUseCase2.OnStatusChange>()

    override fun download(item: DownloadableItem): Flow<DownloadUseCase.DownloadStatus> {
        error("use download2 instead")
    }

    override fun download2(item: DownloadableItem) {
        if (!cache.containsKey(item.id))
            notifyItem(item.id, DownloadUseCase.DownloadStatus.Downloading(0))
        startJobs()
    }

    override fun status(item: DownloadableItem): DownloadUseCase.DownloadStatus? {
        return cache[item.id]
    }

    private fun startJobs() {
        if (job?.isActive != true && runningOperations())
            job = doJobs()
    }

    private fun runningOperations(): Boolean {
        return cache.any {
            it.value is DownloadUseCase.DownloadStatus.Downloading
        }
    }

    private fun doJobs() = coroutineScope.launch {
        while (isActive) {
            if (!runningOperations()) {
                cancel()
                job = null
                return@launch
            }
            delay(10)
            cache.forEach { (item, status) ->
                when (status) {
                    is DownloadUseCase.DownloadStatus.Downloading -> {

                        val percent = if (status.percent == 100) {
                            101
                        } else {
                            min(status.percent + (random.nextInt(10)), 100)
                        }

                        if (percent > 100)
                            notifyItem(item, DownloadUseCase.DownloadStatus.Finished)
                        else
                            notifyItem(item, DownloadUseCase.DownloadStatus.Downloading(percent))
                    }
                }
            }
            Log.e("DownloadUseCaseImpl", "tick")
            delay(1_000)
        }
    }

    fun notifyItem(itemId: String, status: DownloadUseCase.DownloadStatus) {
        cache[itemId] = status
        onStatusChangeObservers.find { it.item()?.id == itemId }?.onStatusChange(status)
    }
}