package com.hcdisat.week_three.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hcdisat.week_three.R
import com.hcdisat.week_three.adapters.MusicTracksAdapter
import com.hcdisat.week_three.models.MusicTrack
import com.hcdisat.week_three.presenters.MusicTrackViewContract
import com.hcdisat.week_three.utils.Genre

abstract class BaseFragment: Fragment(), MusicTrackViewContract {

    /**
     * RecyclerView Adapter
     */
    private val tracksAdapter by lazy {
        MusicTracksAdapter {
            findNavController()
                .navigate(
                    R.id.playerFragment,
                    Bundle().apply { putParcelable(MUSIC_TRACK, it) }
                )
        }
    }

    /**
     * Swipe To refresh widget
     */
    protected lateinit var refreshTracks: SwipeRefreshLayout

    /**
     * used too sets what tab to load based on the music genre
     */
    protected var genre: Genre = Genre.ROCK

    /**
     * setup RecyclerView
     */
    protected fun initList(trackList: RecyclerView) {
        trackList.apply {
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = tracksAdapter
        }
    }

    override fun isLoading(loading: Boolean) {

    }

    override fun success(tracks: List<MusicTrack>): MusicTrackViewContract {
        tracksAdapter.setTracks(tracks)
        refreshTracks.isRefreshing = false
        return this
    }

    override fun error(throwable: Throwable): MusicTrackViewContract {
        Log.e(RockFragment::class.simpleName, throwable.printStackTrace().toString())

        return this
    }
}