package com.sample.experiments.ui.items.purchase

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.sample.experiments.R
import com.sample.experiments.domain.FeedbackItem
import com.sample.experiments.domain.PurchaseItem
import com.sample.experiments.domain.PurchaseItem.Companion.EXTRA_PURCHASE
import com.sample.experiments.ui.FragmentViewComponent
import com.sample.experiments.ui.MVPFragment
import com.sample.experiments.ui.items.feedback.FeedbackItemFragmentComponent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item.*

const val COMPONENT_TYPE_PURCHASE = "COMPONENT_TYPE_PURCHASE"

@AndroidEntryPoint
class PurchaseComponentFragment :
    MVPFragment<PurchaseItemView, PurchaseItemPresenter>(R.layout.item),
    PurchaseItemView,
    FragmentViewComponent {

    companion object{
        operator fun invoke(item : PurchaseItem) : PurchaseComponentFragment =
            PurchaseComponentFragment().also {
                it.arguments = bundleOf(
                    EXTRA_PURCHASE to item
                )
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.updateItem(
            arguments?.getParcelable(EXTRA_PURCHASE)!!
        )

        button1.setOnClickListener {
            presenter.onBidButtonClick()
        }
        button2.setOnClickListener {
            presenter.onPurchaseButtonClick()
        }
    }

    override fun showTitle(title: String) {
        titleLabel.text = title
    }

    override fun setInputValue(value: String) {
        editText.setText(value)
    }

    override fun showInput(show: Boolean) {
        editText.isVisible = show
    }

    override fun showPurchaseButton(show: Boolean) {
        button2.isVisible = show
    }

    override fun showBidButton(show: Boolean) {
        button1.isVisible = show
    }

    override fun showMessage(message: String) {
        Toast.makeText(view!!.context, message, Toast.LENGTH_SHORT).show()
    }

    override fun initializeDagger() {
    }

    override fun getPresenterView(): PurchaseItemView =
        this

    override val fragment: Fragment
        get() = this

    override val type: String
        get() = COMPONENT_TYPE_PURCHASE
}