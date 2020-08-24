package com.sample.experiments.ui.items

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BindingViewHolder<Model> (view : View): RecyclerView.ViewHolder(view) {
    abstract fun bind(model : Model)
}