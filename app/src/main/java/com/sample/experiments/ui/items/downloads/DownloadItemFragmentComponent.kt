package com.sample.experiments.ui.items.downloads

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.sample.experiments.R
import com.sample.experiments.di.ViewHoldersEntryPoint
import com.sample.experiments.domain.DownloadableItem
import com.sample.experiments.domain.DownloadableItem.Companion.EXTRA_DOWNLOADABLE
import com.sample.experiments.domain.FeedbackItem
import com.sample.experiments.ui.FragmentViewComponent
import com.sample.experiments.ui.MVPFragment
import com.sample.experiments.ui.items.MVPViewHolder
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.android.synthetic.main.item3.*

const val COMPONENT_TYPE_DOWNLOAD = "COMPONENT_TYPE_DOWNLOAD"

@AndroidEntryPoint
class DownloadItemFragmentComponent(
) : MVPFragment<DownloadItemView, DownloadItemPresenter>(R.layout.item3),
    DownloadItemView,
    FragmentViewComponent {

    companion object{
        operator fun invoke(item : DownloadableItem) : DownloadItemFragmentComponent =
            DownloadItemFragmentComponent().also {
                it.arguments = bundleOf(
                    EXTRA_DOWNLOADABLE to item
                )
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.updateItem(arguments!!.getParcelable(EXTRA_DOWNLOADABLE)!!)

        button.setOnClickListener {
            presenter.onDownloadItemClick()
        }
    }

    override fun showTitle(title: String) {
        titleLabel.text = title
    }

    override fun showButton(show: Boolean) {
        button.isVisible = show
    }

    override fun showProgress(show: Boolean) {
        progress.isVisible = show
    }

    override fun setProgress(value: Int) {
        progress.progress = value
    }

    override fun showDoneText(show: Boolean) {
        doneText.isVisible = show
    }

    override fun initializeDagger() {
    }

    override fun getPresenterView(): DownloadItemView =
        this

    override val fragment: Fragment
        get() = this
    override val type: String
        get() = COMPONENT_TYPE_DOWNLOAD

}