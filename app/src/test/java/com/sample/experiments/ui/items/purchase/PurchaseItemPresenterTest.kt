package com.sample.experiments.ui.items.purchase

import com.sample.experiments.domain.Permissions
import com.sample.experiments.domain.PurchaseItem
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
class PurchaseItemPresenterTest {

    @RelaxedMockK
    lateinit var view: PurchaseItemView

    @ParameterizedTest(name = "if the item is {0} the displayed title should be {1}")
    @MethodSource("itemsWithTitles")
    fun `show the item title`(item: PurchaseItem, title: String) {
        PurchaseItemPresenter(item, view)
        verify(exactly = 1) { view.showTitle(title) }
    }

    @ParameterizedTest(name = "if the item is {0} the displayed value should be {1}")
    @MethodSource("itemsWithValues")
    fun `show the item value`(item: PurchaseItem, value: String) {
        PurchaseItemPresenter(item, view)
        verify(exactly = 1) { view.setInputValue(value) }
    }

    @ParameterizedTest(name = "if the item is {0} the bid button visibility should be {1}")
    @MethodSource("itemsWithDisplayBidButton")
    fun `show the item bid button`(item: PurchaseItem, show: Boolean) {
        PurchaseItemPresenter(item, view)
        verify(exactly = 1) { view.showBidButton(show) }
    }

    @ParameterizedTest(name = "if the item is {0} the buy button visibility should be {1}")
    @MethodSource("itemsWithDisplayBuyButton")
    fun `show the item buy button`(item: PurchaseItem, show: Boolean) {
        PurchaseItemPresenter(item, view)
        verify(exactly = 1) { view.showPurchaseButton(show) }
    }

    private companion object {
        @JvmStatic
        fun itemsWithTitles() =
            Stream.of(
                Arguments.of(PurchaseItem("Item1", "", Permissions.ALL), "Item1"),
                Arguments.of(PurchaseItem("", "", Permissions.ALL), ""),
                Arguments.of(PurchaseItem("item 2", "", Permissions.ALL), "item 2")
            )

        @JvmStatic
        fun itemsWithValues() =
            Stream.of(
                Arguments.of(PurchaseItem("", "Item1", Permissions.ALL), "Item1"),
                Arguments.of(PurchaseItem("", "", Permissions.ALL), ""),
                Arguments.of(PurchaseItem("", "item 2", Permissions.ALL), "item 2")
            )

        @JvmStatic
        fun itemsWithDisplayBidButton() =
            Stream.of(
                Arguments.of(PurchaseItem("", "", Permissions.ALL), true),
                Arguments.of(PurchaseItem("", "", Permissions.BID_ONLY), true),
                Arguments.of(PurchaseItem("", "", Permissions.NONE), false),
                Arguments.of(PurchaseItem("", "", Permissions.PURCHASE_ONLY), false)
            )

        @JvmStatic
        fun itemsWithDisplayBuyButton() =
            Stream.of(
                Arguments.of(PurchaseItem("", "", Permissions.ALL), true),
                Arguments.of(PurchaseItem("", "", Permissions.BID_ONLY), false),
                Arguments.of(PurchaseItem("", "", Permissions.NONE), false),
                Arguments.of(PurchaseItem("", "", Permissions.PURCHASE_ONLY), true)
            )
    }
}