package com.lbc.domain.use_case

import com.lbc.domain.model.Album
import com.lbc.domain.repository.AlbumRepository
import com.lbc.domain.utils.Resource
import javax.inject.Inject

class GetAlbumsImpl @Inject constructor(
    private val repository: AlbumRepository
) : GetAlbums {
    override suspend operator fun invoke(): Resource<List<Album>> = repository.getAlbums()
}