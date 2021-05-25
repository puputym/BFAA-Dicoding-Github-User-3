package com.puput.github.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.puput.github.model.FavoriteUser

@Database(
    entities = [FavoriteUser::class],
    version = 1
)
abstract class UserDB : RoomDatabase() {
    companion object {
        var INSTANCE: UserDB? = null

        fun getDatabase(context: Context): UserDB? {
            if (INSTANCE == null) {
                synchronized(UserDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserDB::class.java,
                        "database user"
                    ).build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun userFavoriteDao(): FavoriteUserDao
}