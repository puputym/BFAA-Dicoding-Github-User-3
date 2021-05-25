package com.puput.github.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_user")
data class FavoriteUser(
    val login: String,
    val avatar_url : String,
    @PrimaryKey
    val id: Int,
):Serializable
