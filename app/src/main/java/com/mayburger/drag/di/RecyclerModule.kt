package com.mayburger.drag.di

import android.app.Activity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mayburger.drag.adapter.TaskAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
object
RecyclerModule {

    @Provides
    internal fun provideLinearLayoutManager(activity: Activity): LinearLayoutManager {
        return LinearLayoutManager(activity)
    }

    @Provides
    internal fun provideTaskAdapter(): TaskAdapter {
        return TaskAdapter()
    }


}