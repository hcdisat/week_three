package com.hcdisat.week_three.di

import com.hcdisat.week_three.views.*
import dagger.Component

@Component(
    modules = [
        ApplicationModule::class,
        NetworkModule::class,
        PresentersModule::class
    ]
)
interface MusicTrackComponent {
    fun inject(rockFragment: RockFragment)
    fun inject(popFragment: PopFragment)
    fun inject(classicFragment: ClassicFragment)
    fun inject(playerFragment: PlayerFragment)
}