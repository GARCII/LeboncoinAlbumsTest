package com.lbc.data.remote

import com.lbc.data.remote.dto.AlbumResponse
import com.lbc.data.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

interface AlbumServiceApi {
    @GET(Constants.GET_ALBUMS)
    suspend fun getAlbums(): Response<List<AlbumResponse>>
}