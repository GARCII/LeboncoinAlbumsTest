package com.lbc.data.di

import com.lbc.data.db.dao.AlbumDao
import com.lbc.data.remote.AlbumServiceApi
import com.lbc.data.source.LocalAlbumDataSourceImpl
import com.lbc.data.source.RemoteAlbumDataSourceImpl
import com.lbc.domain.source.LocalAlbumDataSource
import com.lbc.domain.source.RemoteAlbumDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SourceModule {

    @Provides
    @Singleton
    fun providesRemoteAlbumDataSource(api: AlbumServiceApi): RemoteAlbumDataSource =
        RemoteAlbumDataSourceImpl(api)

    @Provides
    @Singleton
    fun providesLocalAlbumDataSource(dao: AlbumDao): LocalAlbumDataSource =
        LocalAlbumDataSourceImpl(dao)
}