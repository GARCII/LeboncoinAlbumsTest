package com.lbc.data.source

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lbc.data.utils.FileReader
import com.lbc.data.di.NetworkModule
import com.lbc.data.remote.AlbumServiceApi
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/**
   Another way to test endpoints with MockWebServer
 **/

@RunWith(AndroidJUnit4::class)
class MockWebServerRemoteTest : TestCase() {

    private lateinit var albumServiceApi: AlbumServiceApi
    private lateinit var client: OkHttpClient
    private val mockWebServer = MockWebServer()
    private val port = 8080
    private val baseUrl = "http://localhost:$port"

    @Before
    fun setup() {
        client = NetworkModule.providesOkHttpInterceptorClient()
        albumServiceApi = Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AlbumServiceApi::class.java)
        mockWebServer.start(port)
    }

    @Test
    fun fetchAlbum_success_albumsReturned() = runBlocking {
        val mockedResponse = MockResponse()
        mockedResponse.setResponseCode(200)
        mockedResponse.setBody(FileReader.readStringFromFile("success_response.json"))
        mockWebServer.url(baseUrl)
        mockWebServer.enqueue(mockedResponse)
        val result = albumServiceApi.getAlbums()
        assertEquals(200, result.code())
    }

    @Test
    fun fetchAlbum_error_403_albumsNotReturned() = runBlocking {
        manageError(403)
        val result = albumServiceApi.getAlbums()
        assertEquals(403, result.code())
    }

    @Test
    fun fetchAlbum_error_500_albumsNotReturned() = runBlocking {
        manageError(500)
        val result = albumServiceApi.getAlbums()
        assertEquals(500, result.code())
    }

    private fun manageError(errorCode: Int) {
        val mockedResponse = MockResponse()
        mockedResponse.setResponseCode(errorCode)
        mockedResponse.setBody("{}")
        mockWebServer.url(baseUrl)
        mockWebServer.enqueue(mockedResponse)
    }

    @After
    @Throws(IOException::class)
    fun teardown() {
        mockWebServer.shutdown()
    }
}