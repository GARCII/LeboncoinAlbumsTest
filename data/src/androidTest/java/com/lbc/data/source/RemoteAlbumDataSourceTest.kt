package com.lbc.data.source

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lbc.data.FakeAlbumServiceApi
import com.lbc.data.di.NetworkModule
import com.lbc.data.remote.AlbumServiceApi
import com.lbc.domain.source.RemoteAlbumDataSource
import com.lbc.domain.utils.NetworkStatus
import com.squareup.moshi.Moshi
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RemoteAlbumDataSourceTest : TestCase() {

    private lateinit var albumServiceApi: AlbumServiceApi
    private lateinit var remoteAlbumDataSource: RemoteAlbumDataSource
    private lateinit var client: OkHttpClient
    private lateinit var moshi: Moshi

    @Before
    fun setup() {
        client = NetworkModule.providesOkHttpInterceptorClient()
        moshi = NetworkModule.providesMoshi()
        albumServiceApi = NetworkModule.providesAlbumServiceApi(client, moshi)
    }

    @Test
    fun fetchAlbum_success_albumsReturned() = runBlocking {
        remoteAlbumDataSource = RemoteAlbumDataSourceImpl(albumServiceApi)
        val response = remoteAlbumDataSource.fetchAlbums()
        assertEquals(response.networkStatus, NetworkStatus.SUCCESS)
        assertNotNull(response.data)
        assertTrue(!response.data.isNullOrEmpty())
    }

    @Test
    fun fetchAlbum_error_albumsNotReturned() = runBlocking {
        albumServiceApi = FakeAlbumServiceApi()
        remoteAlbumDataSource = RemoteAlbumDataSourceImpl(albumServiceApi)
        val networkStatus = remoteAlbumDataSource.fetchAlbums().networkStatus
        assertEquals(networkStatus, NetworkStatus.FAILED)
    }
}