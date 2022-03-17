package com.hcdisat.week_three.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hcdisat.week_three.MusicTrackApp
import com.hcdisat.week_three.databinding.FragmentRockBinding
import com.hcdisat.week_three.presenters.RockPresenter
import com.hcdisat.week_three.utils.Genre
import javax.inject.Inject

/**
 * Represents the Rock tab
 */
class RockFragment : BaseFragment() {

    private var _binding: FragmentRockBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    /**
     * Presenter [RockFragment] injection
     */
    @Inject
    lateinit var mPresenter: RockPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // notifies Dagger to inject dependencies for this fragment
        MusicTrackApp.musicTrackComp.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // sets the binding
        _binding = FragmentRockBinding.inflate(inflater, container, false)

        mPresenter.viewContract = this
        // start to monitor the network
        mPresenter.runNetworkMonitor()

        // sets progress bar
        progressBar = binding.loading

        // sets refresh tabs
        refreshTracks = binding.refreshTracks

        // swipeRefresh implementation
        refreshTracks.setOnRefreshListener {
            mPresenter.loadTracks(Genre.ROCK)
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

        mPresenter.loadTracks(Genre.ROCK)
    }

    /**
     * destroys fragment
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}