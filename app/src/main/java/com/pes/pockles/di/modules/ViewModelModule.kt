package com.pes.pockles.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pes.pockles.di.util.ViewModelFactory
import com.pes.pockles.di.util.ViewModelKey
import com.pes.pockles.view.ui.chat.AllChatsViewModel
import com.pes.pockles.view.ui.chat.ChatViewModel
import com.pes.pockles.view.ui.editpock.EditPockViewModel
import com.pes.pockles.view.ui.editprofile.EditProfileViewModel
import com.pes.pockles.view.ui.likes.LikedPocksViewModel
import com.pes.pockles.view.ui.login.LaunchActivityViewModel
import com.pes.pockles.view.ui.login.register.RegisterActivityViewModel
import com.pes.pockles.view.ui.login.register.RegisterIconViewModel
import com.pes.pockles.view.ui.map.MapViewModel
import com.pes.pockles.view.ui.newpock.NewPockViewModel
import com.pes.pockles.view.ui.pockshistory.PocksHistoryViewModel
import com.pes.pockles.view.ui.profile.ProfileViewModel
import com.pes.pockles.view.ui.settings.SettingsViewModel
import com.pes.pockles.view.ui.viewpock.ViewPockViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Module that lists all the [ViewModel] available so they can be created by the [ViewModelFactory]
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun profileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    abstract fun mapViewModel(viewModel: MapViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewPockViewModel::class)
    abstract fun newPockViewModel(viewModel: NewPockViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PocksHistoryViewModel::class)
    abstract fun pocksHistoryViewModel(viewModel: PocksHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ViewPockViewModel::class)
    abstract fun viewPockViewModel(viewModel: ViewPockViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditPockViewModel::class)
    abstract fun editPockViewModel(viewModel: EditPockViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LaunchActivityViewModel::class)
    abstract fun launchActivityViewModel(viewModel: LaunchActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegisterActivityViewModel::class)
    abstract fun registerActivityViewModel(viewModel: RegisterActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegisterIconViewModel::class)
    abstract fun registerIconViewModel(viewModel: RegisterIconViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LikedPocksViewModel::class)
    abstract fun likedPocksViewModel(viewModel: LikedPocksViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditProfileViewModel::class)
    abstract fun EditProfileViewModel(viewModel: EditProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AllChatsViewModel::class)
    abstract fun allChatsViewModel(viewModel: AllChatsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChatViewModel::class)
    abstract fun chatViewModel(viewModel: ChatViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun settingsViewModel(viewModel: SettingsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}