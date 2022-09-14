package com.art.book.repository

import androidx.lifecycle.LiveData
import com.art.book.model.Art
import com.art.book.model.ImageResponse
import com.art.book.utils.Resource

interface IArtRepository {

    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art: Art)

    fun getArt(): LiveData<List<Art>>

    suspend fun searchImage(url: String) : Resource<ImageResponse>

}