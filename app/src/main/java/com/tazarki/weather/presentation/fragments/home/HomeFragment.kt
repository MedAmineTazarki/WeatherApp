package com.tazarki.weather.presentation.fragments.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.tazarki.weather.R
import com.tazarki.weather.databinding.FragmentHomeBinding
import com.tazarki.weather.presentation.fragments.base.BaseFragment


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.weatherFragment)
        }

    }

}