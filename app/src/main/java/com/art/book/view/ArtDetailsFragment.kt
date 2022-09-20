package com.art.book.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.art.book.R
import com.art.book.databinding.FragmentArtDetailsBinding
import com.art.book.utils.Status
import com.art.book.viewmodel.ArtViewModel
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArtDetailsFragment @Inject constructor(
    val glideRequestManager: RequestManager
): Fragment(R.layout.fragment_art_details) {

    private var fragmentArtDetailsBinding: FragmentArtDetailsBinding? = null

    lateinit var viewModel: ArtViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]
        val binding = FragmentArtDetailsBinding.bind(view)
        fragmentArtDetailsBinding = binding

        subScribeToObserver()

        binding.artImageView.setOnClickListener {
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToArtImageSearchFragment())
        }

        val callBack = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.setSelectedImage("")
                findNavController().popBackStack()
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(callBack)

        binding.saveBtn.setOnClickListener {
            viewModel.makeArt(binding.nameText.text.toString(),
                binding.artText.text.toString(),
                binding.yearText.text.toString())

        }

    }

    private fun subScribeToObserver() {
        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer{ url->
            fragmentArtDetailsBinding?.let { binding ->
                glideRequestManager.load(url).into(binding.artImageView)
            }
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer{
            when(it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertArgMsg()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_SHORT).show()
                }

                Status.LOADING -> {

                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentArtDetailsBinding = null
    }
}