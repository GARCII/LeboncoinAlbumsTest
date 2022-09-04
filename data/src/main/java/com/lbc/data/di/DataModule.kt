package com.lbc.data.di

import android.app.Application
import androidx.room.Room
import com.lbc.data.db.AlbumsDatabase
import com.lbc.data.db.AlbumsDatabase.Companion.DATABASE_NAME
import com.lbc.data.db.dao.AlbumDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providesAlbumDatabase(application: Application): AlbumsDatabase =
        Room.databaseBuilder(
            application,
            AlbumsDatabase::class.java,
            DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun providesAlbumDao(database: AlbumsDatabase): AlbumDao = database.getAlbumDao()
}