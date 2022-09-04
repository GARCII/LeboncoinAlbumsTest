package com.lbc.data.db.dao

import androidx.room.*
import com.lbc.data.db.model.Album

@Dao
interface AlbumDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbums(albums: List<Album>)

    @Query("SELECT * FROM album")
    suspend fun getAllAlbums(): List<Album>
}