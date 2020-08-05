package com.sample.experiments.ui.items.feedback

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.sample.experiments.R
import com.sample.experiments.di.ViewHoldersEntryPoint
import com.sample.experiments.domain.FeedbackItem
import com.sample.experiments.domain.FeedbackItem.Companion.EXTRA_FEEDBACK
import com.sample.experiments.ui.FragmentViewComponent
import com.sample.experiments.ui.MVPFragment
import com.sample.experiments.ui.items.downloads.DownloadItemFragmentComponent
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.android.synthetic.main.item2.*

const val COMPONENT_TYPE_FEEDBACK = "COMPONENT_TYPE_FEEDBACK"

@AndroidEntryPoint
class FeedbackItemFragmentComponent :
    MVPFragment<FeedbackItemView, FeedbackPresenter>(R.layout.item2),
    FeedbackItemView,
    FragmentViewComponent {

    companion object{
        operator fun invoke(item : FeedbackItem) : FeedbackItemFragmentComponent =
            FeedbackItemFragmentComponent().also {
                it.arguments = bundleOf(
                    EXTRA_FEEDBACK to item
                )
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.updateItem(arguments?.getParcelable(EXTRA_FEEDBACK)!!)
        button2.setOnClickListener {
            presenter.onButtonClick()
        }
    }

    override fun showTitle(title: String) {
        titleLabel.text = title
    }

    override fun showButton(show: Boolean) {
        button2.isVisible = show
    }

    override fun showMessage(message: String) {
        Toast.makeText(context!!, message, Toast.LENGTH_SHORT).show()
    }

    override fun initializeDagger() {
    }

    override fun getPresenterView(): FeedbackItemView =
        this

    override val fragment: Fragment
        get() = this

    override val type: String
        get() = COMPONENT_TYPE_FEEDBACK
}