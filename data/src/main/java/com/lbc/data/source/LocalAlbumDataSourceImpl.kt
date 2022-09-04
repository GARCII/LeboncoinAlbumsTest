package com.lbc.data.source

import com.lbc.data.db.dao.AlbumDao
import com.lbc.data.remote.dto.fromDomain
import com.lbc.data.remote.dto.toDomain
import com.lbc.domain.model.Album
import com.lbc.domain.source.LocalAlbumDataSource
import javax.inject.Inject

class LocalAlbumDataSourceImpl @Inject constructor(
    private val dao: AlbumDao
) : LocalAlbumDataSource {
    override suspend fun getAlbums(): List<Album> = dao.getAllAlbums().toDomain()

    override suspend fun saveAlbums(albums: List<Album>)  = dao.insertAlbums(albums.fromDomain())
}