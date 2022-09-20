package com.art.book.view

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.art.book.R
import com.art.book.adapter.ArtRecyclerViewAdapter
import com.art.book.databinding.FragmentArtsBinding
import com.art.book.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArtFragment @Inject constructor(
    private val artRecyclerViewAdapter: ArtRecyclerViewAdapter
): Fragment(R.layout.fragment_arts) {

    private var fragmentArtsBinding: FragmentArtsBinding? = null
    lateinit var viewModel: ArtViewModel

    private val swipeCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val selectedArt = artRecyclerViewAdapter.arts[layoutPosition]
            viewModel.deleteArt(selectedArt)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]
        val binding = FragmentArtsBinding.bind(view)
        fragmentArtsBinding = binding

        subscribeToObserver()
        binding.recyclerViewArt.adapter = artRecyclerViewAdapter
        binding.recyclerViewArt.layoutManager = LinearLayoutManager(requireActivity())
        ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.recyclerViewArt)
        binding.fab.setOnClickListener {
            findNavController().navigate(ArtFragmentDirections.actionArtFragment4ToArtDetailsFragment())
        }
    }

    private fun subscribeToObserver(){
        viewModel.artList.observe(viewLifecycleOwner, Observer {
            artRecyclerViewAdapter.arts = it
        })
    }

    override fun onDestroy() {
        fragmentArtsBinding = null
        super.onDestroy()
    }
}