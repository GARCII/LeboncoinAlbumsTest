package com.lbc.data.repository

import com.lbc.domain.model.Album
import com.lbc.domain.repository.AlbumRepository
import com.lbc.domain.source.LocalAlbumDataSource
import com.lbc.domain.source.RemoteAlbumDataSource
import com.lbc.domain.utils.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


import javax.inject.Inject

class AlbumRepositoryImpl @Inject constructor(
    private val remoteAlbumDataSource: RemoteAlbumDataSource,
    private val localAlbumDataSource: LocalAlbumDataSource,
    private val reachability: Reachability,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AlbumRepository {
    override suspend fun getAlbums(): Resource<List<Album>> = withContext(dispatcher) {

        if (reachability.isConnectedToNetwork()) {
            val networkResponse = remoteAlbumDataSource.fetchAlbums()
             when (networkResponse.networkStatus) {
                NetworkStatus.SUCCESS -> {
                    remoteAlbumDataSource.fetchAlbums().data?.let {
                        localAlbumDataSource.saveAlbums(it)
                    }
                    Resource.Success(localAlbumDataSource.getAlbums())
                }
                NetworkStatus.FAILED -> Resource.Error(
                    networkResponse.errorMessage
                        ?: "Something went wrong, please try later",
                    localAlbumDataSource.getAlbums()
                )

            }
        } else {
            Resource.Error(
                "Check your internet connection, and then retry",
                localAlbumDataSource.getAlbums()
            )
        }
    }
}