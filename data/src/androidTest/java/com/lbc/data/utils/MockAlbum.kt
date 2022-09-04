package com.lbc.data.utils

import com.lbc.domain.model.Album
import java.util.*

object MockAlbum {
     fun getAlbums(): List<Album> {
        val mockAlbumsList = mutableListOf<Album>()
        for (i in 0..5) {
            mockAlbumsList.add(
                Album(
                    i,
                    i,
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(),
                )
            )
        }
        return mockAlbumsList
    }
}