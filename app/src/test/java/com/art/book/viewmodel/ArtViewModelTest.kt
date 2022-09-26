package com.art.book.viewmodel

import com.art.book.repository.FakeArtRepository
import org.junit.Before

class ArtViewModelTest {

    private lateinit var viewModel: ArtViewModel

    @Before
    fun setUp() {
        //Test Doubles

        viewModel = ArtViewModel(FakeArtRepository())
    }
}