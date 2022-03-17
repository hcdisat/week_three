package com.hcdisat.week_three.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.fragment.findNavController
import com.hcdisat.week_three.MusicTrackApp
import com.hcdisat.week_three.R
import com.hcdisat.week_three.databinding.FragmentPlayerBinding
import com.hcdisat.week_three.models.MusicTrack
import com.hcdisat.week_three.presenters.PlayerPresenter
import com.hcdisat.week_three.presenters.PlayerViewContract
import com.hcdisat.week_three.utils.LOG_TAG
import com.squareup.picasso.Picasso
import javax.inject.Inject

const val MUSIC_TRACK = "MUSIC_TRACK"

class PlayerFragment : Fragment(), PlayerViewContract {

    private var _binding: FragmentPlayerBinding? = null

    private val binding get() = _binding!!

    private var track: MusicTrack? = null

    @Inject lateinit var presenter: PlayerPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            track = it.getParcelable(MUSIC_TRACK) as? MusicTrack
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // activates DI
        MusicTrackApp.musicTrackComp.inject(this)

        presenter.viewContract = this

        // sets the binding
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        bindTrack()

        binding.playerCloseBtn.setOnClickListener {
            presenter.releasePlayer()
            findNavController().popBackStack()
        }

        binding.btnPlayPause.setOnClickListener {
            presenter.playPauseTrack()
        }

        return binding.root
    }

    private fun bindTrack() {
        track?.let {
            binding.playerArtist.text = it.artistName
            binding.playerTrackTitle.text = it.trackName
            Picasso.get()
                .load(it.artworkUrl)
                .placeholder(R.drawable.ic_place_holder)
                .error(R.drawable.ic_broken_image)
                .into(binding.playerCover)

            presenter.playTrack(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun resumed() {
        Log.d(LOG_TAG, "resumed")
        binding.btnPlayPause
            .setImageDrawable(
                AppCompatResources.getDrawable(requireContext(), R.drawable.ic_pause)
            )
    }

    override fun paused() {
        Log.d(LOG_TAG, "paused")
        binding.btnPlayPause
            .setImageDrawable(
                AppCompatResources.getDrawable(requireContext(), R.drawable.ic_play)
            )
    }
}