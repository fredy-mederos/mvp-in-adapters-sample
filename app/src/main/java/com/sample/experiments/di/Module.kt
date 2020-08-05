package com.sample.experiments.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sample.experiments.domain.DownloadUseCase
import com.sample.experiments.domain.FormatDate
import com.sample.experiments.domain.GetItemsUseCase
import com.sample.experiments.domain.TimeProvider
import com.sample.experiments.impl.DownloadUseCaseImpl
import com.sample.experiments.impl.FormatDateImpl
import com.sample.experiments.impl.GetItemsUseCaseImpl
import com.sample.experiments.impl.TimeProviderImpl
import com.sample.experiments.ui.items.downloads.DownloadItemPresenter
import com.sample.experiments.ui.items.feedback.FeedbackPresenter
import com.sample.experiments.ui.items.purchase.PurchaseItemPresenter
import com.sample.experiments.ui.items.timer.TimerItemPresenter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Qualifier


@Module
@InstallIn(ActivityComponent::class)
abstract class BindModule {

    @Binds
    @ActivityScoped
    abstract fun bindGetItemsUseCase(impl: GetItemsUseCaseImpl): GetItemsUseCase

    @Binds
    @ActivityScoped
    abstract fun bindFormatDate(impl: FormatDateImpl): FormatDate

    @Binds
    @ActivityScoped
    abstract fun bindProvider(impl: TimeProviderImpl): TimeProvider
}

@Module
@InstallIn(ActivityComponent::class)
class Module {

    @Provides
    @ActivityScoped
    fun downloadUseCase(@ActivityContext context: Context): DownloadUseCase {
        return DownloadUseCaseImpl((context as AppCompatActivity).lifecycleScope)
    }

    @Provides
    @MainThreadScheduler
    fun providesMainThreadScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewHoldersEntryPoint {
    fun getDownloadItemPresenter(): DownloadItemPresenter
    fun getFeedbackPresenter(): FeedbackPresenter
    fun getPurchaseItemPresenter(): PurchaseItemPresenter
    fun getTimerItemPresenter(): TimerItemPresenter
}

@Retention
@Qualifier
annotation class MainThreadScheduler