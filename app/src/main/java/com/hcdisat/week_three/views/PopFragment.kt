package com.hcdisat.week_three.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hcdisat.week_three.MusicTrackApp
import com.hcdisat.week_three.databinding.FragmentPopBinding
import com.hcdisat.week_three.presenters.PopPresenter
import com.hcdisat.week_three.utils.Genre
import javax.inject.Inject

/**
 * Represents the Pop Tab
 */
class PopFragment : BaseFragment() {

    private var _binding: FragmentPopBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    /**
     * Presenter [PopPresenter] injection
     */
    @Inject
    lateinit var mPresenter: PopPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // notifies Dagger to inject dependencies for this fragment
        MusicTrackApp.musicTrackComp.inject(this)
    }

    /**
     * here all data transactions are done
     */
    override fun onResume() {
        super.onResume()

        mPresenter.loadTracks(Genre.POP)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // sets the binding
        _binding = FragmentPopBinding.inflate(layoutInflater, container, false)

        mPresenter.viewContract = this
        // start to monitor the network
        mPresenter.runNetworkMonitor()

        // sets progress bar
        progressBar = binding.loading

        // sets refresh tabs
        refreshTracks = binding.refreshTracks

        // swipeRefresh implementation
        refreshTracks.setOnRefreshListener {
            mPresenter.loadTracks(Genre.POP)
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