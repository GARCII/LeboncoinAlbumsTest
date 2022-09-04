package com.lbc.data.di

import com.lbc.data.repository.AlbumRepositoryImpl
import com.lbc.domain.repository.AlbumRepository
import com.lbc.domain.source.LocalAlbumDataSource
import com.lbc.domain.source.RemoteAlbumDataSource
import com.lbc.domain.utils.Reachability
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesAlbumRepository(
        remoteDataSource: RemoteAlbumDataSource,
        localDataSource: LocalAlbumDataSource,
        reachability: Reachability
    ): AlbumRepository = AlbumRepositoryImpl(
        remoteAlbumDataSource = remoteDataSource,
        localAlbumDataSource = localDataSource,
        reachability = reachability)
}