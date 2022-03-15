package com.hcdisat.week_three.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hcdisat.week_three.utils.Genre

/**
 * Represents the Classic tab
 */
class ClassicFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // sets the endpoint
        endpoint = Genre.CLASSIC

        return super.onCreateView(
            inflater,
            container,
            savedInstanceState
        )
    }
}