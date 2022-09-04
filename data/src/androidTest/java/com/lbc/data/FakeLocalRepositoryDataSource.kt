package com.lbc.data

import com.lbc.data.utils.MockAlbum
import com.lbc.domain.model.Album
import com.lbc.domain.source.LocalAlbumDataSource

class FakeLocalRepositoryDataSource : LocalAlbumDataSource {

    override suspend fun getAlbums(): List<Album> = MockAlbum.getAlbums()

    override suspend fun saveAlbums(albums: List<Album>) { }
}