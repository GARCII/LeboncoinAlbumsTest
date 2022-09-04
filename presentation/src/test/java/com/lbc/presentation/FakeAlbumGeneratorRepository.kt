package com.lbc.presentation

import com.lbc.domain.model.Album
import com.lbc.domain.repository.AlbumRepository
import com.lbc.domain.utils.Resource
import com.lbc.presentation.model.AlbumUi
import com.lbc.presentation.model.toAlbumUi
import java.util.*
import kotlin.collections.ArrayList

class FakeAlbumGeneratorRepository(albumListSize: Int) : AlbumRepository {

    private val albums = ArrayList<Album>()

    init {
        for (i in 1..albumListSize + 1) {
            albums.add(mockAlbums(i))
        }
    }
    override suspend fun getAlbums(): Resource<List<Album>> = Resource.Success(albums)

    private fun mockAlbums(id: Int): Album {
        return Album(
            id,
            1,
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        )
    }

    fun getFakeAlbums(): List<AlbumUi> {
        return albums.map { it.toAlbumUi() }
    }
}