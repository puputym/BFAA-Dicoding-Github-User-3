package com.puput.github.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.puput.github.model.FavoriteUser

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserFavorite(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM favorite_user")
    fun getFavoriteUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM favorite_user")
    fun findUser(): Cursor

    @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.login = :login")
    suspend fun cekFavoriteUser2(login: String): String

    @Query("DELETE FROM favorite_user WHERE favorite_user.login = :login")
    suspend fun deleteFromFavoriteUser2(login: String): Int


}