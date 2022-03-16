package com.hcdisat.week_three.monitors

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import com.hcdisat.week_three.utils.LOG_TAG
import io.reactivex.Completable
import io.reactivex.functions.Action

class MediaPlayerMonitor(
    private var context: Context? = null
) {
    /**
     * creates a [MediaPlayer] instance
     */
    private var mediaPlayer : MediaPlayer? = MediaPlayer().apply {
        AudioAttributes
            .Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .build()}

     fun isPlaying(): Boolean = mediaPlayer?.isPlaying ?: false

    /**
     * attempts to play the provided [Uri]
     * used only to load and play a track
     * use playPause method to play/pause operations
     */
    fun play(uri: Uri) {
        if (isPlaying())
            return

        context?.let {
            mediaPlayer?.setDataSource(it, uri)
            val action = Action { prepareMediaPlayer() }
            Completable.fromAction(action)
                .subscribe(
                    { mediaPlayer?.start() },
                    {
                        Log.e(LOG_TAG, "play: Error trying to play audio")
                    }
                ).dispose()
        }
    }

    /**
     * resume or pause media player
     */
    fun playPause() {
        if (isPlaying()) {
            mediaPlayer?.pause()

            return
        }
        mediaPlayer?.start()
    }

    /**
     * dispose of [MediaPlayer]
     */
    fun destroy() {
        mediaPlayer?.apply {
            stop()
            release()
        }

        mediaPlayer = null
    }

    /**
     * this method must be call from another thread
     */
    private fun prepareMediaPlayer() {
        mediaPlayer?.prepare()
    }
}