package com.pes.pockles.di.modules

import com.pes.pockles.view.ui.MainActivity
import com.pes.pockles.view.ui.editpock.EditPockActivity
import com.pes.pockles.view.ui.likes.LikedPocksActivity
import com.pes.pockles.view.ui.login.LaunchActivity
import com.pes.pockles.view.ui.login.register.RegisterActivity
import com.pes.pockles.view.ui.login.register.RegisterActivityIcon
import com.pes.pockles.view.ui.newpock.NewPockActivity
import com.pes.pockles.view.ui.pockshistory.PocksHistoryActivity
import com.pes.pockles.view.ui.viewpock.ViewPockActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Activities modules where all the activities are listed.
 *
 * They are listed as [ContributesAndroidInjector] so they have an injector generated
 * automatically, thus we have not to worry about more that put the new activities here.
 *
 * All dependencies will be auto injected (indicating @Inject to the injected param or constructor)
 */
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

    @ContributesAndroidInjector
    abstract fun contributeEditPockActivity(): EditPockActivity

    @ContributesAndroidInjector
    abstract fun contributeLaunchActivity(): LaunchActivity

    @ContributesAndroidInjector
    abstract fun contributeRegisterActivity(): RegisterActivity

    @ContributesAndroidInjector
    abstract fun contributeRegisterActivityIcon(): RegisterActivityIcon

    @ContributesAndroidInjector
    abstract fun contributeLikedPocksActivity(): LikedPocksActivity


}