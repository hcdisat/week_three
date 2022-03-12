package com.hcdisat.week_three.monitors

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.test.withTestContext

/**
 * Class Used to monitor network connectivity
 */
class NetworkMonitor(
    private var context: Context? = null,
    private val connectionManager: ConnectivityManager =
        context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager,
    private val networkRequest: NetworkRequest =
        NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
): ConnectivityManager.NetworkCallback() {

    /**
     * observes network connectivity changes
     * [BehaviorSubject<Boolean>]
     */
    private val networkObserver  by lazy {
        BehaviorSubject.createDefault(false)
    }

    /**
     * checks if there's connection
     */
    private fun checkConnection(): Boolean {
        connectionManager.getNetworkCapabilities(connectionManager.activeNetwork)?.let {
            return it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }

        return false
    }

    /**
     * registers the this class as a network callback
     * this method must be called in order to receive network state updates
     */
    fun registerForNetworkUpdates(): BehaviorSubject<Boolean> {
        connectionManager.registerNetworkCallback(networkRequest, this)
        networkObserver.onNext(checkConnection())

        return networkObserver
    }

    fun unRegisterForNetworkUpdated() {
        connectionManager.unregisterNetworkCallback(this)
        context = null
    }

    /**
     * Called when there is connection
     */
    override fun onAvailable(network: Network) {
        super.onAvailable(network)
       networkObserver.onNext(true)
    }

    /**
     * Called when the connection is lost
     */
    override fun onLost(network: Network) {
        super.onLost(network)
       networkObserver.onNext(false)
    }
}