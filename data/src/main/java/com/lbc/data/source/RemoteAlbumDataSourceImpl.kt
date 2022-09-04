package com.lbc.data.source

import com.lbc.data.remote.AlbumServiceApi
import com.lbc.data.remote.dto.fromResponse
import com.lbc.domain.model.Album
import com.lbc.domain.source.RemoteAlbumDataSource
import com.lbc.domain.utils.NetworkResponse
import com.lbc.domain.utils.NetworkStatus
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RemoteAlbumDataSourceImpl @Inject constructor(
    private val api: AlbumServiceApi
) : RemoteAlbumDataSource {
    override suspend fun fetchAlbums(): NetworkResponse<List<Album>> {
        return try {
            val response = api.getAlbums()
            if (response.isSuccessful && response.body() != null) {
                NetworkResponse(NetworkStatus.SUCCESS, response.body()!!.fromResponse())
            } else {
                NetworkResponse(
                    NetworkStatus.FAILED,
                    errorMessage = "Something went wrong ! Please retry later"
                )
            }

        } catch (e: HttpException) {
            NetworkResponse(
                NetworkStatus.FAILED,
                errorMessage = e.localizedMessage ?: "An unexpected error occurred"
            )
        } catch (e: IOException) {
            NetworkResponse(
                NetworkStatus.FAILED,
                errorMessage = e.localizedMessage
                    ?: "Couldn't reach server. Check your internet connection"
            )
        }
    }
}