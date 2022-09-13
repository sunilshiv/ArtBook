package com.art.book.di

import android.content.Context
import androidx.room.Room
import com.art.book.dao.ArtDao
import com.art.book.datasource.local.RoomArtDataSource
import com.art.book.datasource.remote.RetrofitApi
import com.art.book.utils.AppConstants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, RoomArtDataSource::class.java, "ArtBookDB").build()

    @Singleton
    @Provides
    fun injectDao(
        database: RoomArtDataSource
    ) = database.artDao()

    @Singleton
    @Provides
    fun injectRetrofitApi() : RetrofitApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitApi::class.java)
    }

}