package com.pes.pockles.di.modules

import com.pes.pockles.view.ui.MainActivity
import com.pes.pockles.view.ui.newpock.NewPockActivity
import com.pes.pockles.view.ui.pockshistory.PocksHistoryActivity
import com.pes.pockles.view.ui.viewpock.ViewPockActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {
    @ContributesAndroidInjector(modules = [MainActivityFragmentModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeNewPockActivity(): NewPockActivity

    @ContributesAndroidInjector
    abstract fun contributePockHistoryActivity(): PocksHistoryActivity

    @ContributesAndroidInjector
    abstract fun contributeViewPockActivity(): ViewPockActivity
}