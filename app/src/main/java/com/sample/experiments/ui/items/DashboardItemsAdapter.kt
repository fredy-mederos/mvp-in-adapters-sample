package com.sample.experiments.ui.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sample.experiments.R
import com.sample.experiments.domain.*
import com.sample.experiments.ui.items.downloads.DownloadItemViewHolder
import com.sample.experiments.ui.items.feedback.FeedbackItemViewHolder
import com.sample.experiments.ui.items.purchase.PurchaseItemViewHolder

private const val TYPE_PURCHASE = 0;
private const val TYPE_FEEDBACK = 1;
private const val TYPE_DOWNLOAD = 2;

class DashboardItemsAdapter constructor(
    private val items: List<DashboardItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_PURCHASE -> PurchaseItemViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
            )
            TYPE_FEEDBACK -> FeedbackItemViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item2, parent, false)
            )
            TYPE_DOWNLOAD -> DownloadItemViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item3, parent, false)
            )
            else -> error("unknown type: $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (val item = items[position]) {
            is PurchaseItem -> TYPE_PURCHASE
            is FeedbackItem -> TYPE_FEEDBACK
            is DownloadableItem -> TYPE_DOWNLOAD
            else -> error("unknown type: $item")
        }
    }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when {
            item is PurchaseItem && holder is PurchaseItemViewHolder -> holder.bind(item)
            item is FeedbackItem && holder is FeedbackItemViewHolder -> holder.bind(item)
            item is DownloadableItem && holder is DownloadItemViewHolder -> holder.bind(item)
            else -> error("unknown type: $item")
        }
    }
}
