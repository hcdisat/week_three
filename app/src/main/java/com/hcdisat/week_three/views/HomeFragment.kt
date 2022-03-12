package com.hcdisat.week_three.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hcdisat.week_three.data.network.GenreTracksService
import com.hcdisat.week_three.databinding.FragmentHomeBinding
import com.hcdisat.week_three.models.GenreSummary
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.buttonFirst.setOnClickListener {
//            //findNavController().navigate(R.id.action_HomeFragment_to_SecondFragment)
//        }
    }

    override fun onResume() {
        super.onResume()

        GenreTracksService.run {
            retrofit.getPopTracks().enqueue(object: Callback<GenreSummary> {
                override fun onResponse(call: Call<GenreSummary>, response: Response<GenreSummary>) {

                }

                override fun onFailure(call: Call<GenreSummary>, t: Throwable) {

                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}