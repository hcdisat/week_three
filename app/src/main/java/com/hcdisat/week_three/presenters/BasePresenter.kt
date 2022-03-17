package com.hcdisat.week_three.presenters

import com.hcdisat.week_three.data.database.AppDbRepository
import com.hcdisat.week_three.data.network.MusicApiRepositoryContract
import com.hcdisat.week_three.models.GenreSummary
import com.hcdisat.week_three.utils.Genre
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

abstract class BasePresenter(
    private val musicApiRepository: MusicApiRepositoryContract,
    private var dbRepository: AppDbRepository
): MusicTrackPresenterContract {

    protected val compositeDisposable by lazy {
        CompositeDisposable()
    }

    protected var isConnected = true

    private lateinit var _viewContract: MusicTrackViewContract
    var viewContract: MusicTrackViewContract
    get() = _viewContract
    set(value) {
        _viewContract = value
    }

    /**
     * calls API endpoint based on passed genre
     */
    protected fun callApi(genre: Genre) =
        getResourceObserver(genre)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            { saveTracks(it, genre) },
            { viewContract.error(it)}
        ).also { compositeDisposable.add(it) }

    /**
     * gets tracks from database by genre
     */
    protected fun readFromDatabase(genre: Genre) =
        dbRepository.let { repo ->
            repo.allTracks(genre)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { viewContract.success(it) },
                    { viewContract.error(it) }
                ).also { compositeDisposable.add(it) }
    }

    /**
     * return the observer resource to subscribe
     */
    private fun getResourceObserver(endpoint: Genre): Single<GenreSummary> {
        return when(endpoint) {
            Genre.ROCK -> musicApiRepository.getRock()
            Genre.POP ->  musicApiRepository.getPop()
            Genre.CLASSIC ->  musicApiRepository.getClassic()
        }
    }

    /**
     * insert tracks in database
     */
    private fun saveTracks(tracks: GenreSummary, genre: Genre) {
        dbRepository.let { repo ->
            repo.insert(tracks.musicTracks, genre) // query tracks from the API
                .andThen(repo.allTracks(genre)) // get items from DB
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ viewContract.success(it) }, { viewContract.error(it) })
                .also { compositeDisposable.add(it) }
        }
    }
}