package com.sample.experiments.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sample.experiments.domain.*
import com.sample.experiments.impl.*
import com.sample.experiments.ui.items.downloads.DownloadItemPresenter
import com.sample.experiments.ui.items.downloads.DownloadItemPresenter2
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
import kotlinx.coroutines.GlobalScope
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
    @ActivityScoped
    fun downloadUseCase2(@ActivityContext context: Context): DownloadUseCase2 {
        return DownloadUseCase2Impl((context as AppCompatActivity).lifecycleScope)
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
    fun getDownloadItemPresenter2(): DownloadItemPresenter2
    fun getFeedbackPresenter(): FeedbackPresenter
    fun getPurchaseItemPresenter(): PurchaseItemPresenter
    fun getTimerItemPresenter(): TimerItemPresenter
}

@Retention
@Qualifier
annotation class MainThreadScheduler