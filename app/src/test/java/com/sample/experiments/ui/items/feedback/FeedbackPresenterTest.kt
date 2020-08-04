package com.sample.experiments.ui.items.feedback

import com.sample.experiments.domain.FeedbackItem
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class FeedbackPresenterTest {

    @RelaxedMockK
    lateinit var view: FeedbackItemView

    @ParameterizedTest(name = "if the item is {0} the displayed title should be {1}")
    @MethodSource("itemsWithTitles")
    fun `show the item title`(item: FeedbackItem, title: String) {
        val presenter = FeedbackPresenter()
        presenter.view = view
        presenter.updateItem(item)
        verify(exactly = 1) { view.showTitle(title) }
    }

    @ParameterizedTest(name = "if the item is {0} the button visibility should be {1}")
    @MethodSource("itemsWithButtonsAvailability")
    fun `show the button if available`(item: FeedbackItem, visibility: Boolean) {
        val presenter = FeedbackPresenter()
        presenter.view = view
        presenter.updateItem(item)
        verify(exactly = 1) { view.showButton(visibility) }
    }

    @ParameterizedTest(name = "if the item is {0} the onclick message should be {1}")
    @MethodSource("itemsAndExpectedMessages")
    fun `show the message when click`(item: FeedbackItem, message: String) {
        val presenter = FeedbackPresenter()
        presenter.view = view
        presenter.updateItem(item)
        presenter.onButtonClick(item)
        verify(exactly = 1) { view.showMessage(message) }
    }

    private companion object {
        @JvmStatic
        fun itemsWithTitles() =
            Stream.of(
                Arguments.of(FeedbackItem("Item1", false), "Item1"),
                Arguments.of(FeedbackItem("", false), ""),
                Arguments.of(FeedbackItem("item 2", false), "item 2")
            )

        @JvmStatic
        fun itemsWithButtonsAvailability() =
            Stream.of(
                Arguments.of(FeedbackItem("", false), false),
                Arguments.of(FeedbackItem("", true), true)
            )

        @JvmStatic
        fun itemsAndExpectedMessages() =
            Stream.of(
                Arguments.of(FeedbackItem("Item1", false), "Click on: Item1"),
                Arguments.of(FeedbackItem("", false), "Click on: "),
                Arguments.of(FeedbackItem("item 2", false), "Click on: item 2")
            )
    }
}

