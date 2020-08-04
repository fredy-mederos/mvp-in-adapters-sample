package com.sample.experiments.ui.items

import android.view.View
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancelChildren

abstract class MVPViewHolder<V : BasePresenter.View, P : BasePresenter<V>, T>(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    abstract val presenter: P
    abstract fun getView(): V

    @CallSuper
    open fun bindItem(item: T) {
        presenter.onDestroy()
        presenter.view = getView()
    }
}

abstract class BasePresenter<V : BasePresenter.View> : CoroutineScope by MainScope() {

    interface View

    var view: V? = null

    @CallSuper
    open fun onDestroy() {
        coroutineContext.cancelChildren()
        view = null
    }
}