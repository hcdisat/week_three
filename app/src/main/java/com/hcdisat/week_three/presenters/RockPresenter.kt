package com.hcdisat.week_three.presenters

import com.hcdisat.week_three.data.database.AppDbRepository
import com.hcdisat.week_three.data.network.MusicApiRepositoryContract
import com.hcdisat.week_three.monitors.NetworkMonitor
import com.hcdisat.week_three.utils.Genre
import javax.inject.Inject

class RockPresenter @Inject constructor(
    private val musicApiRepository: MusicApiRepositoryContract,
    private var networkMonitor: NetworkMonitor,
    private var dbRepository: AppDbRepository
) : BasePresenter(musicApiRepository, networkMonitor, dbRepository) {

    /**
     * keeps an eye on the network
     * used to react when connection is lost
     */
    override fun runNetworkMonitor() {
        networkMonitor.apply {
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
        viewContract.isLoading(true)

        if (isConnected) {
            callApi(genre)
            return
        }

        readFromDatabase(genre)
    }
}