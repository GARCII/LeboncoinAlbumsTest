package com.lbc.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Album(
    @PrimaryKey
    @ColumnInfo(name = "titleId") val titleId: Int,
    @ColumnInfo(name = "albumId") val albumId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "thumbnailUrl") val thumbnailUrl: String
) : Serializable