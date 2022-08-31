package com.art.book.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.art.book.model.Art

@Dao
interface ArtDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArt(art: Art)

    @Delete
    suspend fun deleteArt(art: Art)

    @Query("SELECT * FROM arts")
    fun observeArts(): LiveData<List<Art>>
}