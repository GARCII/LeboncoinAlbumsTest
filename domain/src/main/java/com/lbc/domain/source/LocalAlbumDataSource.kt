package com.lbc.domain.source

import com.lbc.domain.model.Album

interface LocalAlbumDataSource {
    suspend fun getAlbums(): List<Album>
    suspend fun saveAlbums(albums: List<Album>)
}