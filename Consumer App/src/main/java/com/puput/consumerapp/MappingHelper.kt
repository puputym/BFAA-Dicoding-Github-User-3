package com.puput.consumerapp

import android.database.Cursor

object MappingHelper {
    fun mapCursorToArrayList(favoriteCursor: Cursor?): ArrayList<UserList> {
        val favoritelist = ArrayList<UserList>()

        favoriteCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(ContractDatabase.FavoriteUser._ID))
                val username = getString(getColumnIndexOrThrow(ContractDatabase.FavoriteUser.USERNAME))
                val avatar = getString(getColumnIndexOrThrow(ContractDatabase.FavoriteUser.AVATAR))
                favoritelist.add(UserList(username, id, avatar))
            }
        }
        return favoritelist
    }
}