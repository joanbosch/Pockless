package com.pes.pockles.di.component

import android.app.Application
import com.pes.pockles.PocklesApplication
import com.pes.pockles.di.modules.ActivitiesModule
import com.pes.pockles.di.modules.AppModule
import com.pes.pockles.di.modules.DaoModule
import com.pes.pockles.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DaoModule::class,
        AndroidInjectionModule::class,
        ActivitiesModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent: AndroidInjector<PocklesApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}