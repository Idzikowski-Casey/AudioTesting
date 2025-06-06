package com.idzcasey.wavesofsilence.presenters

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.hilt.ScreenModelKey
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(ActivityComponent::class)
abstract class PresenterModule {
    @Binds
    @IntoMap
    @ScreenModelKey(HomePresenter::class)
    abstract fun bindsHomeScreenModel(homeScreenModel: HomePresenter): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(LibraryPresenter::class)
    abstract fun bindsLibraryScreenModel(libraryScreenModel: LibraryPresenter): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(AllSoundsPresenter::class)
    abstract fun bindsAllSoundsScreenModel(allSoundsScreenModel: AllSoundsPresenter): ScreenModel
}