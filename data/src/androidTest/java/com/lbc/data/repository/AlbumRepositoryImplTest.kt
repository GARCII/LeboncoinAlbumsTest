package com.lbc.data.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lbc.data.FakeAlbumServiceApi
import com.lbc.data.ReachabilityConnected
import com.lbc.data.remote.AlbumServiceApi
import com.lbc.data.FakeLocalRepositoryDataSource
import com.lbc.data.ReachabilityNotConnected
import com.lbc.data.source.RemoteAlbumDataSourceImpl
import com.lbc.domain.model.Album
import com.lbc.domain.repository.AlbumRepository
import com.lbc.domain.source.LocalAlbumDataSource
import com.lbc.domain.source.RemoteAlbumDataSource
import com.lbc.domain.utils.Reachability
import com.lbc.domain.utils.Resource
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumRepositoryImplTest : TestCase() {

    private lateinit var fakeAlbumServiceApi: AlbumServiceApi
    private lateinit var fakeLocalRepository: LocalAlbumDataSource
    private lateinit var fakeRemoteRepository: RemoteAlbumDataSource
    private lateinit var albumRepository: AlbumRepository
    private lateinit var fakeReachability: Reachability

    @Before
    fun setup() {
        fakeReachability = ReachabilityConnected()
        fakeAlbumServiceApi = FakeAlbumServiceApi()
        fakeRemoteRepository = RemoteAlbumDataSourceImpl(fakeAlbumServiceApi)
        fakeLocalRepository = FakeLocalRepositoryDataSource()
    }

    @Test
    fun getAlbums_error_api_albumsReturnedLocally() = runBlocking {
        albumRepository = AlbumRepositoryImpl(fakeRemoteRepository, fakeLocalRepository, fakeReachability)
        if (albumRepository.getAlbums() is Resource.Error) {
            val result = albumRepository.getAlbums() as Resource.Error<List<Album>>
            assertTrue(!result.data.isNullOrEmpty())
        }
    }

    @Test
    fun getAlbums_not_connected_network_albumsReturnedLocally() = runBlocking {
        fakeReachability = ReachabilityNotConnected()
        albumRepository = AlbumRepositoryImpl(fakeRemoteRepository, fakeLocalRepository, fakeReachability)
        if (albumRepository.getAlbums() is Resource.Error) {
            val result = albumRepository.getAlbums() as Resource.Error<List<Album>>
            assertTrue(!result.data.isNullOrEmpty())
        }
    }
}