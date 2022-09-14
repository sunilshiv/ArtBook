package com.art.book.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.art.book.model.Art
import com.art.book.model.ImageResponse
import com.art.book.repository.IArtRepository
import com.art.book.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArtViewModel @Inject constructor(
    private val iArtRepository: IArtRepository
): ViewModel() {

    //Art Fragment
    val artList = iArtRepository.getArt()

    //Image search api fragment
    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList: LiveData<Resource<ImageResponse>>
    get() = images

    private val selectedImage = MutableLiveData<String>()
    val selectedImageUrl: LiveData<String>
    get() = selectedImage

    //Art details fragment
    private var insertArtMsg = MutableLiveData<Resource<Art>>()
    val insertArtMessage: LiveData<Resource<Art>>
    get() = insertArtMsg

    fun resetInsertArgMsg() {
        insertArtMsg = MutableLiveData<Resource<Art>>()
    }

    fun setSelectedImage(url: String) {
        selectedImage.postValue(url)
    }

    fun insertArt(art: Art) = viewModelScope.launch {
        iArtRepository.insertArt(art)
    }

    fun makeArt(name: String, artistName: String, year: String) {
        if(name.isEmpty() || artistName.isEmpty() || year.isEmpty()) {
            insertArtMsg.postValue(Resource.error("Enter art, artistname and year", null))
            return
        }

        val yearInt = try {
            year.toInt()
        }catch (ex: Exception){
            insertArtMsg.postValue(Resource.error("Year should be a number", null))
        }

        val art = Art(name, artistName, yearInt as Int, selectedImageUrl.value ?: "")
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(art))
    }

    fun searchForImage(searchString: String) {

        if (searchString.isEmpty()) {
            return
        }

        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = iArtRepository.searchImage(searchString)
            images.value = response
        }
    }

}