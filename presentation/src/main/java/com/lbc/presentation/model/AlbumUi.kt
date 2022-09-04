package com.lbc.presentation.model

import com.lbc.domain.model.Album

data class AlbumUi(
    val albumId: Int,
    val id: Int,
    val title: String,
    val thumbnailUrl: String,
    val url: String
)

fun Album.toAlbumUi() = AlbumUi(
    albumId, id, title, thumbnailUrl, url
)