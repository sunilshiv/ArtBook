package com.art.book.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.art.book.dao.ArtDao
import com.art.book.model.Art

@Database(entities = [Art::class], version = 1)
abstract class RoomArtDataSource: RoomDatabase() {

    abstract fun artDao(): ArtDao
}