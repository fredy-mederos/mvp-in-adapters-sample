package com.sample.experiments.ui.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sample.experiments.R
import com.sample.experiments.domain.DashboardItem
import com.sample.experiments.ui.items.timer.*

private const val TYPE_PURCHASE = 0;
private const val TYPE_FEEDBACK = 1;
private const val TYPE_DOWNLOAD = 2;
private const val TYPE_TIME = 3;

private const val TYPE_SINGLE_ENGINE = 4
private const val TYPE_COPY_PER_ROW = 5
private const val TYPE_ONE_PER_MODEL = 6

class DashboardItemsAdapter constructor(
    //Diffutil is usually recommended. but here because we are coping one of the types to update
    // it's required
) : ListAdapter<DashboardItem, RecyclerView.ViewHolder>(DashboardDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_COPY_PER_ROW -> CopyPerRowViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item4, parent, false)
            )
            TYPE_ONE_PER_MODEL -> OnePerModelViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item4, parent, false)
            )
            TYPE_SINGLE_ENGINE -> SingleEngineTimerViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item4, parent, false)
            )
            else -> error("unknown type: $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (val item = getItem(position)) {
            is SingleEngineTimerUIModel -> TYPE_SINGLE_ENGINE
            is CopyPerRowUIModel -> TYPE_COPY_PER_ROW
            is OnePerModelUIModel -> TYPE_ONE_PER_MODEL
            else -> error("unknown type: $item")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when {
            item is CopyPerRowUIModel && holder is CopyPerRowViewHolder -> holder.bind(item)
            item is OnePerModelUIModel && holder is OnePerModelViewHolder -> holder.bind(item)
            item is SingleEngineTimerUIModel && holder is SingleEngineTimerViewHolder -> holder.bind(item)
            else -> error("unknown type: $item")
        }
    }
}

class DashboardDiff : DiffUtil.ItemCallback<DashboardItem>() {
    override fun areItemsTheSame(oldItem: DashboardItem, newItem: DashboardItem): Boolean {
        if (oldItem::class != newItem::class) {
            return false
        }

        if (oldItem is SingleEngineTimerUIModel) {
            newItem as SingleEngineTimerUIModel
            return oldItem.id == newItem.id
        }

        if (oldItem is OnePerModelUIModel) {
            newItem as OnePerModelUIModel
            return oldItem.endDate.time == newItem.endDate.time
        }


        if (oldItem is CopyPerRowUIModel) {
            newItem as CopyPerRowUIModel
            return oldItem.endDate.time == newItem.endDate.time
        }

        error("oldItem type=$oldItem is not supported")
    }

    override fun areContentsTheSame(oldItem: DashboardItem, newItem: DashboardItem): Boolean {
        if (oldItem::class != newItem::class) {
            return false
        }

        if (oldItem is SingleEngineTimerUIModel) {
            newItem as SingleEngineTimerUIModel
            return (oldItem as SingleEngineTimerUIModel) == (newItem as SingleEngineTimerUIModel)
        }

        if (oldItem is OnePerModelUIModel) {
            newItem as OnePerModelUIModel
            return (oldItem as OnePerModelUIModel) == (newItem as OnePerModelUIModel)
        }


        if (oldItem is CopyPerRowUIModel) {
            newItem as CopyPerRowUIModel
            return (oldItem as CopyPerRowUIModel) == (newItem as CopyPerRowUIModel)
        }

        error("oldItem type=$oldItem is not supported")
    }
}
