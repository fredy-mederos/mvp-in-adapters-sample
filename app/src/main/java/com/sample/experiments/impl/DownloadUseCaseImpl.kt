package com.sample.experiments.impl

import android.util.Log
import com.sample.experiments.domain.DownloadUseCase
import com.sample.experiments.domain.DownloadableItem
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlin.math.min
import kotlin.random.Random

private class ChannelItem() {
    private var channel = Channel<DownloadUseCase.DownloadStatus>(Channel.CONFLATED)
    var lastValue: DownloadUseCase.DownloadStatus? = null
        private set

    fun offerValue(value: DownloadUseCase.DownloadStatus) {
        lastValue = value
        channel.offer(value)
    }

    fun consumeAsFlow(): Flow<DownloadUseCase.DownloadStatus> {
        if (channel.isClosedForSend)
            channel = Channel(Channel.CONFLATED)
        return channel.consumeAsFlow()
    }
}

class DownloadUseCaseImpl(private val coroutineScope: CoroutineScope) :
    DownloadUseCase {

    private val cache: HashMap<String, ChannelItem> = HashMap()

    private var job: Job? = null
    private val random = Random

    override fun download(item: DownloadableItem): Flow<DownloadUseCase.DownloadStatus> {
        val channel = cache.getOrPut(item.id) {
            ChannelItem().apply {
                offerValue(DownloadUseCase.DownloadStatus.Downloading(0))
            }
        }
        startJobs()
        return channel.consumeAsFlow()
    }

    override fun status(item: DownloadableItem): DownloadUseCase.DownloadStatus? {
        return cache[item.id]?.lastValue
    }

    private fun startJobs() {
        if (job?.isActive != true && runningOperations())
            job = doJobs()
    }

    private fun runningOperations(): Boolean {
        return cache.any {
            it.value.lastValue is DownloadUseCase.DownloadStatus.Downloading
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
            cache.values.forEach { channel ->
                when (val status = channel.lastValue) {
                    is DownloadUseCase.DownloadStatus.Downloading -> {

                        val percent = if (status.percent == 100) {
                            101
                        } else {
                            min(status.percent + (random.nextInt(10)), 100)
                        }

                        if (percent > 100)
                            channel.offerValue(DownloadUseCase.DownloadStatus.Finished)
                        else
                            channel.offerValue(DownloadUseCase.DownloadStatus.Downloading(percent))
                    }
                }
            }
            Log.e("DownloadUseCaseImpl","tick")
            delay(1_000)
        }
    }
}