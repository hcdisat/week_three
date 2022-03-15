package com.hcdisat.week_three.presenters

import android.content.Context
import android.net.Uri
import android.util.Log
import com.hcdisat.week_three.data.network.GenreTracksService
import com.hcdisat.week_three.models.GenreSummary
import com.hcdisat.week_three.models.MusicTrack
import com.hcdisat.week_three.monitors.MediaPlayerMonitor
import com.hcdisat.week_three.utils.Genre
import com.hcdisat.week_three.utils.LOG_TAG
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MusicTracksPresenter(
    private var context: Context? = null,
    private var viewContract: MusicTrackViewContract? = null,
): MusicTrackPresenterContract {

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    /**
     * return the observer resource to subscribe
     */
    private fun getResourceObserver(endpoint: Genre): Single<GenreSummary> {
        return when(endpoint) {
            Genre.ROCK -> GenreTracksService.retrofit.getRockTracks()
            Genre.POP -> GenreTracksService.retrofit.getPopTracks()
            Genre.CLASSIC -> GenreTracksService.retrofit.getClassicTracks()
        }
    }

    /**
     * loads tracks from repository source
     */
    override fun getTracks(endpoint: Genre) {
        viewContract?.let {
            it.isLoading(true)
            getResourceObserver(endpoint)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response ->
                        it.success(response).isLoading(false)
                    },
                    { error ->
                        it.error(error).isLoading(false)
                    }
                ).apply { compositeDisposable.add(this) }
        }
    }

    override fun destroy() {
        context = null
        viewContract = null
        compositeDisposable.dispose()
    }

//    override fun playTrack(musicTrack: MusicTrack) {
//        val uri = Uri.parse(musicTrack.previewUrl)
//        mediaPlayerMonitor?.play(uri)
//    }
}

/**
 * Contract must be implemented by an Activity or
 * Fragment in order to communicate with the presenter
 */
interface MusicTrackViewContract {

    /**
     * loading data from repository
     */
    fun isLoading(loading: Boolean)

    /**
     * gets all tracks from repository
     */
    fun success(tracks: GenreSummary): MusicTrackViewContract

    /**
     * get any error produced in the observable
     */
    fun error(throwable: Throwable): MusicTrackViewContract
}

/**
 * Contract to be implemented by the view using this presenter
 */
interface MusicTrackPresenterContract {

    /**
     * gets tracks from network or db
     * this method works in the background
     */
    fun getTracks(endpoint: Genre)

    /**
     * dispose of presenter resources
     */
    fun destroy()
}