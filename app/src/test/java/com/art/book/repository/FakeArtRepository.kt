package com.art.book.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.art.book.model.Art
import com.art.book.model.ImageResponse
import com.art.book.utils.Resource

class FakeArtRepository: IArtRepository {

    private val arts = mutableListOf<Art>()
    private val artsLiveData = MutableLiveData<List<Art>>(arts)

    override suspend fun insertArt(art: Art) {
       arts.add(art)
    }

    override suspend fun deleteArt(art: Art) {
        arts.remove(art)
    }

    override fun getArt(): LiveData<List<Art>> {
       return artsLiveData
    }

    override suspend fun searchImage(url: String): Resource<ImageResponse> {
       return Resource.success(ImageResponse(listOf(), 0, 0))
    }

    private fun refreshData(){
        artsLiveData.postValue(arts)
    }
}