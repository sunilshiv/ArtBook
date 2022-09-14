package com.art.book.view.fragfactory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.art.book.view.ArtDetailsFragment
import com.bumptech.glide.RequestManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val glideRequestManager: RequestManager
): FragmentFactory() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            ArtDetailsFragment::class.java.name -> ArtDetailsFragment(glideRequestManager)
            else -> super.instantiate(classLoader, className)
        }
    }


}