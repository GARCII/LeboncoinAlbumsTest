package com.lbc.domain.source

import com.lbc.domain.model.Album
import com.lbc.domain.utils.NetworkResponse

interface RemoteAlbumDataSource {
    suspend fun fetchAlbums(): NetworkResponse<List<Album>>
}