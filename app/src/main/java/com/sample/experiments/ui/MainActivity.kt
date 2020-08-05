package com.sample.experiments.ui

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.sample.experiments.R
import com.sample.experiments.domain.*
import com.sample.experiments.ui.items.downloads.DownloadItemFragmentComponent
import com.sample.experiments.ui.items.feedback.COMPONENT_TYPE_FEEDBACK
import com.sample.experiments.ui.items.feedback.FeedbackItemFragmentComponent
import com.sample.experiments.ui.items.purchase.PurchaseComponentFragment
import com.sample.experiments.ui.items.purchase.COMPONENT_TYPE_PURCHASE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), DashBoardView {

    @Inject
    lateinit var presenter: DashboardPresenter

    //order is important
    private val listOfComponents: List<FragmentViewComponent> = listOf(
        FeedbackItemFragmentComponent(FeedbackItem("Item WithFeedback", true)),
        PurchaseComponentFragment(PurchaseItem("Item 1", "100", Permissions.ALL)),
        DownloadItemFragmentComponent(DownloadableItem("1", "Download 1"))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.view = this

        bindComponents()
    }

    private fun bindComponents() {
        listOfComponents.forEach(::bindComponent)
    }

    private fun bindComponent(component: FragmentViewComponent) {
        val fragmentParent = FrameLayout(this)
        fragmentParent.id = ViewCompat.generateViewId()

        listViewHolder.addView(
            fragmentParent,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )

        supportFragmentManager
            .beginTransaction()
            .replace(
                fragmentParent.id,
                component.fragment,
                component.type
            )
            .commit()
    }

    override fun onResume() {
        super.onResume()
        presenter.loadItems()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.view = null
    }

    override fun showItems(items: List<DashboardItem>) {
    }
}

