package com.hcdisat.week_three.presenters

import android.content.Context
import com.hcdisat.week_three.data.database.AppRepository
import com.hcdisat.week_three.data.network.GenreTracksService
import com.hcdisat.week_three.models.GenreSummary
import com.hcdisat.week_three.models.MusicTrack
import com.hcdisat.week_three.monitors.NetworkMonitor
import com.hcdisat.week_three.utils.Genre
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MusicTracksPresenter(
    private var context: Context? = null,
    private var viewContract: MusicTrackViewContract? = null,
    private var networkMonitor: NetworkMonitor? = NetworkMonitor(context),
    private var dbRepository: AppRepository? = AppRepository(context)
): MusicTrackPresenterContract {

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    private var isConnected = true

    /**
     * calls API endpoint based on passed genre
     */
    private fun callApi(genre: Genre) =
        getResourceObserver(genre)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            { saveTracks(it, genre) },
            { viewContract?.error(it)}
        ).also { compositeDisposable.add(it) }

    /**
     * gets tracks from database by genre
     */
    private fun readFromDatabase(genre: Genre) =
        dbRepository?.let { repo ->
            repo.allTracks(genre)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { viewContract?.success(it) },
                    { viewContract?.error(it) }
                ).also { compositeDisposable.add(it) }
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
     * insert tracks in database
     */
    private fun saveTracks(tracks: GenreSummary, genre: Genre) {
        dbRepository?.let { repo ->
            repo.deleteAll(genre)
                .andThen(repo.insert(tracks.musicTracks, genre))
                .andThen(repo.allTracks(genre))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ viewContract?.success(it) }, { viewContract?.error(it) })
                .also { compositeDisposable.add(it) }
        }
    }

    /**
     * keeps an eye on the network
     * used to react when connection is lost
     */
    override fun runNetworkMonitor() {
        networkMonitor?.apply {
            registerForNetworkUpdates().subscribe(
                { isConnected = it },
                { isConnected = false }
            ).let {  compositeDisposable.add(it) }
        }
    }

    /**
     * loads tracks from repository source
     */
    override fun loadTracks(genre: Genre) {
        viewContract?.isLoading(true)

        if (isConnected) {
            callApi(genre)
            return
        }

        readFromDatabase(genre)
    }

    /**
     * destroy presenter and free resources
     */
    override fun destroy() {
        context = null
        viewContract = null
        compositeDisposable.dispose()
        dbRepository?.destroy()
        dbRepository = null
    }
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
    fun success(tracks: List<MusicTrack>): MusicTrackViewContract

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
    fun loadTracks(endpoint: Genre)

    /**
     * dispose of presenter resources
     */
    fun destroy()

    /**
     * keeps an eye on the network
     * used to react when connection is lost
     */
    fun runNetworkMonitor()
}