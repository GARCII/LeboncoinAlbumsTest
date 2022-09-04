package com.lbc.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lbc.data.db.dao.AlbumDao
import com.lbc.data.db.model.Album

@Database(
    entities = [Album::class],
    version = 1,
    exportSchema = false
)

abstract class AlbumsDatabase : RoomDatabase() {

    abstract fun getAlbumDao(): AlbumDao

    companion object {
        const val DATABASE_NAME = "AlbumsDatabase"
    }
}