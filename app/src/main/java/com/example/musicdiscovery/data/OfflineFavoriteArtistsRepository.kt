package com.example.musicdiscovery.data

import com.example.musicdiscovery.dao.FavoriteArtistDao
import com.example.musicdiscovery.entity.FavoriteArtistEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class OfflineFavoriteArtistsRepository(private val favoriteArtistDao: FavoriteArtistDao): FavoriteArtistsRepository {
    override fun getAllFavoriteArtistsStream(): Flow<List<FavoriteArtistEntity>> = favoriteArtistDao.getAllFavoriteArtists()

    override fun getFavoriteArtistStream(id: Long): Flow<FavoriteArtistEntity?> = favoriteArtistDao.getFavoriteArtist(id)

    override suspend fun insertFavoriteArtist(favoriteArtist: FavoriteArtistEntity) = favoriteArtistDao.insert(favoriteArtist)

    override suspend fun deleteFavoriteArtist(favoriteArtist: FavoriteArtistEntity) = favoriteArtistDao.delete(favoriteArtist)

    override suspend fun updateFavoriteArtist(favoriteArtist: FavoriteArtistEntity) = favoriteArtistDao.update(favoriteArtist)
    override suspend fun insertOrDeleteFavoriteArtistIfExists(favoriteArtistEntity: FavoriteArtistEntity): Event {
        var favoriteArtist = getFavoriteArtistStream(favoriteArtistEntity.id).firstOrNull()
        if (favoriteArtist == null) {
            insertFavoriteArtist(favoriteArtistEntity)
            return Event.INSERTED
        } else {
            deleteFavoriteArtist(favoriteArtistEntity)
            return Event.DELETED
        }
    }
}

