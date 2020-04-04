package com.pes.pockles.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pes.pockles.di.util.ViewModelFactory
import com.pes.pockles.di.util.ViewModelKey
import com.pes.pockles.view.ui.map.MapViewModel
import com.pes.pockles.view.ui.newpock.NewPockViewModel
import com.pes.pockles.view.ui.pockshistory.PocksHistoryViewModel
import com.pes.pockles.view.ui.profile.ProfileViewModel
import com.pes.pockles.view.ui.viewpock.ViewPockViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    internal abstract fun profileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    internal abstract fun mapViewModel(viewModel: MapViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewPockViewModel::class)
    internal abstract fun newPockViewModel(viewModel: NewPockViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PocksHistoryViewModel::class)
    internal abstract fun pocksHistoryViewModel(viewModel: PocksHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ViewPockViewModel::class)
    internal abstract fun viewPockViewModel(viewModel: ViewPockViewModel): ViewModel
}