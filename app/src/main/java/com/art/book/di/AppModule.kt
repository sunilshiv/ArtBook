package com.art.book.di

import android.content.Context
import androidx.room.Room
import com.art.book.R
import com.art.book.dao.ArtDao
import com.art.book.datasource.local.RoomArtDataSource
import com.art.book.datasource.remote.RetrofitApi
import com.art.book.repository.ArtRepository
import com.art.book.repository.IArtRepository
import com.art.book.utils.AppConstants.BASE_URL
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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

    @Singleton
    @Provides
    fun injectNormalRepo(dao : ArtDao, api: RetrofitApi) = ArtRepository(dao,api) as IArtRepository

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide
        .with(context).setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        )

}