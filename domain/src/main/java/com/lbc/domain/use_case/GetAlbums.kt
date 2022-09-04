package com.lbc.domain.use_case

import com.lbc.domain.model.Album
import com.lbc.domain.utils.Resource

interface GetAlbums {
     suspend operator fun invoke(): Resource<List<Album>>
}