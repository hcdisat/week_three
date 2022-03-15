package com.hcdisat.week_three

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hcdisat.week_three.models.GenreSummary
import com.hcdisat.week_three.models.MusicTrack
import com.squareup.picasso.Picasso

/**
 * Adapter to hold all tracks in the recycler view
 */
class MusicTracksAdapter(
    private var tracks : GenreSummary? = null,
    private val onTrackClicked: (track: MusicTrack) -> Unit
) : RecyclerView.Adapter<TrackViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setTracks(newTracks: GenreSummary) {
        tracks = newTracks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.track_item, parent, false).apply {
                return TrackViewHolder(this, onTrackClicked)
            }
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        tracks?.musicTracks?.let {
            holder.bind(it[position])
        }
    }

    /**
     * returns how many items are in the adapter
     */
    override fun getItemCount() = tracks?.resultCount ?: 0
}

/**
 * View Holder used by adapter above
 */
class TrackViewHolder(
    viewItem: View,
    private val onTrackClicked: (track: MusicTrack) -> Unit
) : RecyclerView.ViewHolder(viewItem) {

    private val artisCover =
        viewItem.findViewById(R.id.artist_cover) as ImageView

    private val trackTitle =
        viewItem.findViewById(R.id.tract_title) as TextView

    private val trackArtist =
        viewItem.findViewById(R.id.tract_artist) as TextView

    private val trackPriceText =
        viewItem.findViewById(R.id.track_price) as TextView

    fun bind(track: MusicTrack) {
        track.apply {
            trackTitle.text = trackName
            trackArtist.text = artistName
            trackPriceText.text = trackPrice.toString()

            Picasso.get()
                .load(artworkUrl)
                .placeholder(R.drawable.ic_place_holder)
                .error(R.drawable.ic_broken_image)
                .resize(250, 250)
                .into(artisCover)
        }
        itemView.setOnClickListener {
            onTrackClicked(track)
        }
    }
}