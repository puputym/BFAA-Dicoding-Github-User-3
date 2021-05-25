package com.puput.github.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.room.Room
import com.puput.github.database.FavoriteUserDao
import com.puput.github.database.UserDB

class UserContentProvider : ContentProvider() {
    private lateinit var userDao: FavoriteUserDao

    companion object {
        const val AUTHORITY = "com.puput.github"
        const val TABLE_NAME = "favorite_user"
        const val LIST = 1
        const val OBJECT = 2

        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, LIST)
            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", OBJECT)
        }
    }

    override fun onCreate(): Boolean {
        userDao = Room.databaseBuilder(
            context!!, UserDB::class.java, "database user"
        ).fallbackToDestructiveMigration().build().userFavoriteDao()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            LIST -> {
                userDao.findUser()
            }
            else -> null
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}