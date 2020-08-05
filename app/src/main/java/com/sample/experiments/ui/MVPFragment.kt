package com.sample.experiments.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.sample.experiments.ui.items.BasePresenter
import javax.inject.Inject

abstract class MVPFragment<V : BasePresenter.View, P : BasePresenter<V>>(@LayoutRes layout : Int) : Fragment(layout) {

    protected lateinit var presenter: P
        private set

    protected abstract fun initializeDagger()

    protected abstract fun getPresenterView(): V

    @Inject
    protected fun injectPresenter(presenter: P) {
        this.presenter = presenter
    }

    protected open fun onBackPressed() {
        activity?.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeDagger()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.view = getPresenterView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroy()
    }
}