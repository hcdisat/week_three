package com.hcdisat.week_three

import android.app.Application
import com.hcdisat.week_three.di.ApplicationModule
import com.hcdisat.week_three.di.DaggerMusicTrackComponent
import com.hcdisat.week_three.di.MusicTrackComponent

class MusicTrackApp: Application() {

    override fun onCreate() {
        super.onCreate()

        musicTrackComp = DaggerMusicTrackComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    companion object {
        lateinit var musicTrackComp: MusicTrackComponent
    }
}