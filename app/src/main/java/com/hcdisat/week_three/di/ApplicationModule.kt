package com.hcdisat.week_three.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.hcdisat.week_three.data.database.AppDbRepository
import com.hcdisat.week_three.data.database.AppDbRepositoryContract
import com.hcdisat.week_three.data.database.MusicTrackDatabase
import com.hcdisat.week_three.monitors.MediaPlayerMonitor
import com.hcdisat.week_three.monitors.NetworkMonitor
import dagger.Module
import dagger.Provides
import javax.inject.Scope
import javax.inject.Singleton

@Module
class ApplicationModule(
    private val applicationContext: Context
) {

    @Provides
    fun providesApplicationContext(): Context = applicationContext

    @Provides
    fun providesConnectionManager(applicationContext: Context): ConnectivityManager =
        applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Provides
    fun providesNetworkRequest(): NetworkRequest =
        NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()

    @Provides
    fun providesNetworkMonitor(
        applicationContext: Context,
        connectionManager: ConnectivityManager,
        networkRequest: NetworkRequest
    ): NetworkMonitor =
        NetworkMonitor(connectionManager, networkRequest)

    @Provides
    fun providesMediaPlayerMonitor(
        applicationContext: Context
    ): MediaPlayerMonitor = MediaPlayerMonitor(applicationContext)


    @Provides
    fun providesAppDbRepository(
        database: MusicTrackDatabase
    ): AppDbRepositoryContract = AppDbRepository(database)

    @Provides
    fun providesDatabase(
        applicationContext: Context
    ): MusicTrackDatabase = MusicTrackDatabase.getInstance(applicationContext)
}