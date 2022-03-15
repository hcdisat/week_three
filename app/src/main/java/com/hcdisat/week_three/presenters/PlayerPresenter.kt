package com.hcdisat.week_three.presenters

import android.content.Context
import android.net.Uri
import com.hcdisat.week_three.models.MusicTrack
import com.hcdisat.week_three.monitors.MediaPlayerMonitor

class PlayerPresenter(
    private var context: Context? = null,
    private var viewContract: PlayerViewContract? = null,
    private var mediaPlayerMonitor: MediaPlayerMonitor? = MediaPlayerMonitor(context)
): PlayerPresenterContract {

    override fun playTrack(musicTrack: MusicTrack) {
        val uri = Uri.parse(musicTrack.previewUrl)
        mediaPlayerMonitor?.play(uri)
        viewContract?.resumed()
    }

    override fun playPauseTrack() {
        mediaPlayerMonitor?.let {
            if (it.isPlaying())
                viewContract?.paused()
            else {
                viewContract?.resumed()
            }

            it.playPause()
        }
    }

    override fun destroy() {
        mediaPlayerMonitor?.destroy()
        mediaPlayerMonitor = null
        context = null
        viewContract = null
    }
}

interface PlayerViewContract {
    fun resumed()
    fun paused()
}

interface PlayerPresenterContract {
    fun playTrack(musicTrack: MusicTrack)
    fun playPauseTrack()
    fun destroy()
}