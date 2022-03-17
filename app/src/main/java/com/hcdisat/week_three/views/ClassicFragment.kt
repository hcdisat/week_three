package com.hcdisat.week_three.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hcdisat.week_three.MusicTrackApp
import com.hcdisat.week_three.databinding.FragmentClassicBinding
import com.hcdisat.week_three.presenters.ClassicPresenter
import com.hcdisat.week_three.utils.Genre
import javax.inject.Inject

/**
 * Represents the Classic tab
 */
class ClassicFragment : BaseFragment() {

    private var _binding: FragmentClassicBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @Inject
    lateinit var mPresenter: ClassicPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // notifies Dagger to inject dependencies for this fragment
        MusicTrackApp.musicTrackComp.inject(this)
    }

    override fun onResume() {
        super.onResume()

        mPresenter.loadTracks(Genre.CLASSIC)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClassicBinding.inflate(layoutInflater, container, false)

        mPresenter.viewContract = this
        // start to monitor the network
        mPresenter.runNetworkMonitor()

        // sets progress bar
        progressBar = binding.loading

        // sets refresh tabs
        refreshTracks = binding.refreshTracks

        // swipeRefresh implementation
        refreshTracks.setOnRefreshListener {
            mPresenter.loadTracks(Genre.CLASSIC)
        }

        //init the recycler view
        initList(binding.trackList)

        return binding.root
    }

    /**
     * destroys fragment
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}