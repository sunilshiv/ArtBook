package com.art.book.repository

import androidx.lifecycle.LiveData
import com.art.book.dao.ArtDao
import com.art.book.datasource.remote.RetrofitApi
import com.art.book.model.Art
import com.art.book.model.ImageResponse
import com.art.book.utils.Resource
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao: ArtDao,
    private val retrofitApi: RetrofitApi
): IArtRepository {

    override suspend fun insertArt(art: Art) {
       artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(queryString: String): Resource<ImageResponse> {
        return try {
            val response = retrofitApi.imageSearch(queryString)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else{
                Resource.error("Error", null)
            }
        }catch (ex: Exception){
            Resource.error("No DATA!", null)
        }
    }
}