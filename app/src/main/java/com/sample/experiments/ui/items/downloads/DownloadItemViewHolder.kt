package com.sample.experiments.ui.items.downloads

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.sample.experiments.domain.DownloadUseCase
import com.sample.experiments.domain.DownloadableItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item3.*

class DownloadItemViewHolder(
    override val containerView: View,
    private val downloadUseCase: DownloadUseCase
) : RecyclerView.ViewHolder(containerView), LayoutContainer, DownloadItemView {

    lateinit var presenter: DownloadItemPresenter

    fun bind(item: DownloadableItem) {
        if (::presenter.isInitialized)
            presenter.clear()

        presenter = DownloadItemPresenter(item, this, downloadUseCase)


        button.setOnClickListener {
            onButtonClick()
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

    override fun onButtonClick() {
        presenter.onDownloadItemClick()
    }

}