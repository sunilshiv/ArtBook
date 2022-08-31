package com.art.book.view

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.art.book.R
import com.art.book.databinding.FragmentArtDetailsBinding

class ArtDetailsFragment: Fragment(R.layout.fragment_art_details) {

    private var fragmentArtDetailsBinding: FragmentArtDetailsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentArtDetailsBinding.bind(view)
        fragmentArtDetailsBinding = binding

        binding.artImageView.setOnClickListener {
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToArtImageSearchFragment())
        }

        val callBack = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(callBack)

    }

    override fun onDestroy() {
        fragmentArtDetailsBinding = null
        super.onDestroy()
    }
}