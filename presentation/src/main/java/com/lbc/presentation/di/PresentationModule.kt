package com.lbc.presentation.di

import com.lbc.domain.repository.AlbumRepository
import com.lbc.domain.use_case.GetAlbums
import com.lbc.domain.use_case.GetAlbumsImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {

    @Provides
    @Singleton
    fun providesGetAlbumUseCase(
        repository: AlbumRepository
    ): GetAlbums = GetAlbumsImpl(repository)
}