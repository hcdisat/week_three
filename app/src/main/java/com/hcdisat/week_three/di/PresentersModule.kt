package com.hcdisat.week_three.di

import com.hcdisat.week_three.data.database.AppDbRepository
import com.hcdisat.week_three.data.network.MusicApiRepositoryContract
import com.hcdisat.week_three.monitors.MediaPlayerMonitor
import com.hcdisat.week_three.monitors.NetworkMonitor
import com.hcdisat.week_three.presenters.*
import dagger.Module
import dagger.Provides

@Module
class PresentersModule {

    @Provides
    fun providesRockPresenter(
        musicApiRepository: MusicApiRepositoryContract,
        networkMonitor: NetworkMonitor,
        dbRepository: AppDbRepository
    ): RockPresenter = RockPresenter(musicApiRepository, networkMonitor, dbRepository)

    @Provides
    fun providesPopPresenter(
        musicApiRepository: MusicApiRepositoryContract,
        networkMonitor: NetworkMonitor,
        dbRepository: AppDbRepository
    ): PopPresenter = PopPresenter(musicApiRepository, networkMonitor, dbRepository)

    @Provides
    fun providesClassicPresenter(
        musicApiRepository: MusicApiRepositoryContract,
        networkMonitor: NetworkMonitor,
        dbRepository: AppDbRepository
    ): ClassicPresenter = ClassicPresenter(musicApiRepository, networkMonitor, dbRepository)

    @Provides
    fun providesMusicPlayerPresenter(
        mediaPlayerMonitor: MediaPlayerMonitor
    ): PlayerPresenterContract = PlayerPresenter(mediaPlayerMonitor)
}