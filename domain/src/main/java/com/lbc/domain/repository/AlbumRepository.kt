package com.lbc.domain.repository

import com.lbc.domain.model.Album
import com.lbc.domain.utils.Resource

interface AlbumRepository {
    suspend fun getAlbums() : Resource<List<Album>>
}