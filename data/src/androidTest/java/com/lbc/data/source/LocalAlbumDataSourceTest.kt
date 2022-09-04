package com.lbc.data.source

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lbc.data.db.AlbumsDatabase
import com.lbc.data.db.dao.AlbumDao
import com.lbc.data.utils.MockAlbum
import com.lbc.domain.source.LocalAlbumDataSource
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class LocalAlbumDataSourceTest : TestCase() {

    private lateinit var database: AlbumsDatabase
    private lateinit var albumDao: AlbumDao
    private lateinit var localAlbumDataSource: LocalAlbumDataSource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            AlbumsDatabase::class.java
        ).allowMainThreadQueries().build()

        albumDao = database.getAlbumDao()
        localAlbumDataSource = LocalAlbumDataSourceImpl(dao = albumDao)
    }

    @Test
    fun emptyAlbumTable_success_albumTableEmpty() = runBlocking {
        val albums = localAlbumDataSource.getAlbums()
        assertTrue(albums.isEmpty())
    }

    @Test
    fun insertAlbum_success_albumInserted() = runBlocking {
        val albums = MockAlbum.getAlbums()
        localAlbumDataSource.saveAlbums(albums)
        val result = localAlbumDataSource.getAlbums()
        assertTrue(result.isNotEmpty())
    }

    @Test
    fun getAlbums_success_albumsReturned() = runBlocking {
        val albums = MockAlbum.getAlbums()
        localAlbumDataSource.saveAlbums(albums)
        val result = localAlbumDataSource.getAlbums()
        assertEquals(result.size, albums.size)
    }

    @After
    @Throws(IOException::class)
    fun teardown() {
        database.close()
    }
}