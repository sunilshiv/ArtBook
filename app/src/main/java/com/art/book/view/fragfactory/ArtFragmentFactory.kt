package com.art.book.view.fragfactory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.art.book.adapter.ArtRecyclerViewAdapter
import com.art.book.adapter.ImageRecyclerAdapter
import com.art.book.view.ArtDetailsFragment
import com.art.book.view.ArtFragment
import com.art.book.view.ArtImageSearchFragment
import com.bumptech.glide.RequestManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val artRecyclerViewAdapter: ArtRecyclerViewAdapter,
    private val glideRequestManager: RequestManager,
    private val imageRecyclerAdapter: ImageRecyclerAdapter
): FragmentFactory() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            ArtFragment::class.java.name -> ArtFragment(artRecyclerViewAdapter)
            ArtDetailsFragment::class.java.name -> ArtDetailsFragment(glideRequestManager)
            ArtImageSearchFragment::class.java.name -> ArtImageSearchFragment(imageRecyclerAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }


}