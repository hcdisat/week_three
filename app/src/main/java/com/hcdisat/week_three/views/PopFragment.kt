package com.hcdisat.week_three.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hcdisat.week_three.R
import com.hcdisat.week_three.databinding.FragmentPopBinding
import com.hcdisat.week_three.databinding.FragmentRockBinding
import com.hcdisat.week_three.utils.Genre

/**
 * Represents the Pop Tab
 */
class PopFragment : BaseFragment() {

    /**
     * override onCreateView to set the endpoint
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // sets the genre to load
        endpoint = Genre.POP

        return super.onCreateView(
            inflater,
            container,
            savedInstanceState
        )
    }
}