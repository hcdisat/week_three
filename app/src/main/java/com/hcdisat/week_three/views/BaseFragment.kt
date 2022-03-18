package com.hcdisat.week_three.views

import android.app.AlertDialog
import android.content.DialogInterface
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hcdisat.week_three.R
import com.hcdisat.week_three.adapters.MusicTracksAdapter
import com.hcdisat.week_three.models.MusicTrack
import com.hcdisat.week_three.presenters.MusicTrackViewContract
import com.hcdisat.week_three.utils.messageDialog

abstract class BaseFragment: Fragment(), MusicTrackViewContract {

    private var canNavigateTrack = false

    /**
     * RecyclerView Adapter
     */
    private val tracksAdapter by lazy {
        MusicTracksAdapter {
            if (canNavigateTrack) {
                findNavController()
                    .navigate(
                        R.id.playerFragment,
                        Bundle().apply { putParcelable(MUSIC_TRACK, it) }
                    )
            } else {
                messageDialog(
                    requireContext(),
                    "Error: Cannot play track",
                    "No Internet connection.").show()
            }
        }
    }

    /**
     * Tracks Recycler List
     */
    private lateinit var _trackList: RecyclerView

    /**
     * Swipe To refresh widget
     */
    protected lateinit var refreshTracks: SwipeRefreshLayout

    /**
     * Progress bar
     */
    protected lateinit var progressBar: ProgressBar

    /**
     * setup RecyclerView
     */
    protected fun initList(trackList: RecyclerView) {
        trackList.apply {
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = tracksAdapter
        }
        _trackList = trackList
    }

    override fun isLoading(loading: Boolean) {
        if (loading) {
            progressBar.visibility = View.VISIBLE
            _trackList.visibility = View.GONE
            return
        }

        progressBar.visibility = View.GONE
        _trackList.visibility = View.VISIBLE
    }

     override fun isConnected(isConnected: Boolean) {
         canNavigateTrack = isConnected
     }

     override fun success(tracks: List<MusicTrack>): MusicTrackViewContract {
        tracksAdapter.setTracks(tracks)
        refreshTracks.isRefreshing = false
        isLoading(false)
        return this
    }

    override fun error(throwable: Throwable): MusicTrackViewContract {
        Log.e(RockFragment::class.simpleName, throwable.printStackTrace().toString())
        messageDialog(
            requireContext(),
            "Error!!",
            "Something went wrong, please try again later\n${throwable.printStackTrace()}"
        ).show()
        return this
    }
}