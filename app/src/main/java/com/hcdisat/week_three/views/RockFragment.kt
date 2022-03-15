package com.hcdisat.week_three.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hcdisat.week_three.utils.Genre

/**
 * Represents the Rock tab
 */
class RockFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // sets the genre to load
        endpoint = Genre.ROCK

        return super.onCreateView(
            inflater,
            container,
            savedInstanceState
        )
    }
}