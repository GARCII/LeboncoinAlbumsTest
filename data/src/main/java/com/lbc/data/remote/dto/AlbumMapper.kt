package com.lbc.data.remote.dto

import com.lbc.data.db.model.Album

typealias AlbumEntity = com.lbc.domain.model.Album

fun Album.fromDomain() = AlbumEntity(
    id = titleId,
    albumId = albumId,
    title = title,
    url = url,
    thumbnailUrl = thumbnailUrl
)

fun AlbumEntity.toDomain() = Album(
    titleId = id,
    albumId = albumId,
    title = title,
    url = url,
    thumbnailUrl = thumbnailUrl
)

fun AlbumResponse.toDomain() = AlbumEntity(
    id = id,
    albumId = albumId,
    title = title,
    url = url,
    thumbnailUrl = thumbnailUrl
)

fun List<Album>.toDomain(): List<AlbumEntity> = map(Album::fromDomain)

fun List<AlbumEntity>.fromDomain(): List<Album> = map(AlbumEntity::toDomain)

fun List<AlbumResponse>.fromResponse(): List<AlbumEntity> = map(AlbumResponse::toDomain)