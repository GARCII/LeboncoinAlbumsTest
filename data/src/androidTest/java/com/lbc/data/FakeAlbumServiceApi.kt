package com.lbc.data

import com.lbc.data.remote.AlbumServiceApi
import com.lbc.data.remote.dto.AlbumResponse
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class FakeAlbumServiceApi: AlbumServiceApi {

    override suspend fun getAlbums(): Response<List<AlbumResponse>> {
        val responseBody = SERVER_ERROR_MESSAGE.toResponseBody(null)
        return Response.error(500, responseBody)
    }

    companion object {
        const val SERVER_ERROR_MESSAGE = "500 Server Error"
    }
}