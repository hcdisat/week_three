package com.hcdisat.week_three.presenters

import android.net.Uri
import com.hcdisat.week_three.models.MusicTrack
import com.hcdisat.week_three.monitors.MediaPlayerMonitor
import javax.inject.Inject

class PlayerPresenter @Inject constructor(
    private var mediaPlayerMonitor: MediaPlayerMonitor
): PlayerPresenterContract {

    override fun playTrack(musicTrack: MusicTrack) {
        val uri = Uri.parse(musicTrack.previewUrl)
        mediaPlayerMonitor.play(uri)
        viewContract.resumed()
    }

    private lateinit var _viewContract: PlayerViewContract
    var viewContract: PlayerViewContract
    get() = _viewContract
    set(value) { _viewContract = value}

    override fun playPauseTrack() {
        mediaPlayerMonitor.run {
            if (isPlaying()) viewContract.paused()
            else viewContract.resumed()
            playPause()
        }
    }
}

interface PlayerViewContract {
    fun resumed()
    fun paused()
}

interface PlayerPresenterContract {
    fun playTrack(musicTrack: MusicTrack)
    fun playPauseTrack()
}