package com.sample.experiments.ui.items.downloads

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.sample.experiments.di.ViewHoldersEntryPoint
import com.sample.experiments.domain.DownloadableItem
import com.sample.experiments.ui.items.MVPViewHolder
import dagger.hilt.android.EntryPointAccessors
import kotlinx.android.synthetic.main.item3.*

class DownloadItemViewHolder(
    view: View
) : MVPViewHolder<DownloadItemView, DownloadItemPresenter, DownloadableItem>(view),
    DownloadItemView {

    override val presenter: DownloadItemPresenter = EntryPointAccessors.fromActivity(
        containerView.context as AppCompatActivity,
        ViewHoldersEntryPoint::class.java
    ).getDownloadItemPresenter()

    override fun bindItem(item: DownloadableItem) {
        super.bindItem(item)
        presenter.updateItem(item)

        button.setOnClickListener {
            presenter.onDownloadItemClick(item)
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

    override fun getView() = this

}