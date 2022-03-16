package com.hcdisat.week_three.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hcdisat.week_three.MusicTracksAdapter
import com.hcdisat.week_three.R
import com.hcdisat.week_three.databinding.FragmentRockBinding
import com.hcdisat.week_three.models.MusicTrack
import com.hcdisat.week_three.presenters.MusicTrackPresenterContract
import com.hcdisat.week_three.presenters.MusicTrackViewContract
import com.hcdisat.week_three.presenters.MusicTracksPresenter
import com.hcdisat.week_three.utils.Genre

open class BaseFragment: Fragment(), MusicTrackViewContract {

    private var _binding: FragmentRockBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    /**
     * Presenter for Genres views
     */
    private val presenter by lazy {
        MusicTracksPresenter(
            requireContext(),
            this
        ) as MusicTrackPresenterContract
    }

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
     * used too sets what tab to load based on the music genre
     */
    protected var endpoint: Genre = Genre.ROCK

    /**
     * setup RecyclerView
     */
    private fun initList(trackList: RecyclerView) {
        trackList.apply {
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = tracksAdapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // sets the binding
        _binding = FragmentRockBinding.inflate(inflater, container, false)

        // start to monitor the network
        presenter.runNetworkMonitor()

        // swipeRefresh implementation
        binding.refreshTracks.setOnRefreshListener {
            presenter.loadTracks(endpoint)
        }

        //init the recycler view
        initList(binding.trackList)

        return binding.root
    }

    /**
     * here all data transactions are done
     */
    override fun onResume() {
        super.onResume()

        presenter.loadTracks(endpoint)
    }

    /**
     * destroys fragment
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        presenter.destroy()
    }

    override fun isLoading(loading: Boolean) {

    }

    override fun success(tracks: List<MusicTrack>): MusicTrackViewContract {
        tracksAdapter.setTracks(tracks)
        binding.refreshTracks.isRefreshing = false
        return this
    }

    override fun error(throwable: Throwable): MusicTrackViewContract {
        Log.e(RockFragment::class.simpleName, throwable.printStackTrace().toString())

        return this
    }
}