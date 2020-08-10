package com.sample.experiments.ui.items

import android.view.View
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.extensions.LayoutContainer
import kotlinx.coroutines.*

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

    protected val compositeDisposable = CompositeDisposable()
    var view: V? = null

    open fun onDestroy() {
        compositeDisposable.clear()
        coroutineContext.cancelChildren(CancellationException("view $this onDestroy"))
        view = null
    }
}