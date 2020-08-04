package com.sample.experiments.ui.items.downloads

import com.sample.experiments.domain.DownloadUseCase
import com.sample.experiments.domain.DownloadableItem
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
internal class DownloadItemPresenterTest {

    @RelaxedMockK
    lateinit var view: DownloadItemView

    val dispatcher = TestCoroutineDispatcher()
    val scope = TestCoroutineScope(dispatcher)
    val downloadUseCase = DownloadUseCaseTestImpl(scope)

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `test downloading flow`() = scope.runBlockingTest {
        val item = DownloadableItem("1", "Item1")
        val presenter = DownloadItemPresenter(downloadUseCase)
        presenter.view = view
        presenter.updateItem(item)
        downloadUseCase.sequence = listOf(
            DownloadUseCase.DownloadStatus.Downloading(0),
            DownloadUseCase.DownloadStatus.Downloading(10),
            DownloadUseCase.DownloadStatus.Downloading(20),
            DownloadUseCase.DownloadStatus.Downloading(30),
            DownloadUseCase.DownloadStatus.Downloading(100),
            DownloadUseCase.DownloadStatus.Finished
        )

        verify(exactly = 1) { view.showButton(true) }
        verify(exactly = 1) { view.showProgress(false) }
        verify(exactly = 1) { view.showDoneText(false) }
        verify(exactly = 1) { view.showTitle(item.title) }
        verify(exactly = 0) { view.setProgress(any()) }

        presenter.onDownloadItemClick(item)
        advanceUntilIdle()

        verify(exactly = 6) { view.showButton(false) }
        verify(exactly = 5) { view.showProgress(true) }
        verify(exactly = 5) { view.setProgress(any()) }
        verify(exactly = 1) { view.showDoneText(true) }

    }

    @Test
    fun `test downloading flow interrupted`() = scope.runBlockingTest {
        val item = DownloadableItem("1", "Item1")
        var presenter = DownloadItemPresenter(downloadUseCase)
        presenter.view = view
        presenter.updateItem(item)
        downloadUseCase.sequence = listOf(
            DownloadUseCase.DownloadStatus.Downloading(0),
            DownloadUseCase.DownloadStatus.Downloading(10),
            DownloadUseCase.DownloadStatus.Downloading(20),
            DownloadUseCase.DownloadStatus.Downloading(30),
            DownloadUseCase.DownloadStatus.Downloading(40)
        )

        verify(exactly = 1) { view.showButton(true) }
        verify(exactly = 1) { view.showProgress(false) }
        verify(exactly = 1) { view.showDoneText(false) }
        verify(exactly = 1) { view.showTitle(item.title) }
        verify(exactly = 0) { view.setProgress(any()) }

        presenter.onDownloadItemClick(item)
        advanceUntilIdle()


        verify(exactly = 5) { view.showButton(false) }
        verify(exactly = 5) { view.showProgress(true) }
        verify(exactly = 5) { view.setProgress(any()) }

        presenter.clear()
        presenter = DownloadItemPresenter(downloadUseCase)
        presenter.view = view
        presenter.updateItem(item)
        verify(exactly = 5) { view.showButton(false) }
        verify(exactly = 5) { view.showProgress(true) }
        verify(exactly = 5) { view.setProgress(any()) }
    }
}

class DownloadUseCaseTestImpl(private val coroutineScope: CoroutineScope) : DownloadUseCase {

    var sequence: List<DownloadUseCase.DownloadStatus> = emptyList()

    private var channel = Channel<DownloadUseCase.DownloadStatus>(Channel.CONFLATED)
    private var lastValue: DownloadUseCase.DownloadStatus? = null

    override fun download(item: DownloadableItem): Flow<DownloadUseCase.DownloadStatus> {
        runSequence()
        return channel.consumeAsFlow()
    }

    override fun status(item: DownloadableItem): DownloadUseCase.DownloadStatus? {
        return lastValue
    }

    private fun offerValue(value: DownloadUseCase.DownloadStatus) {
        lastValue = value
        channel.offer(value)
    }

    private fun runSequence() = coroutineScope.launch {
        sequence.forEach { item ->
            delay(1)
            offerValue(item)
        }
    }
}