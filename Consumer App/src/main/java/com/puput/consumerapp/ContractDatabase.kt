package com.puput.consumerapp

import android.net.Uri
import android.provider.BaseColumns

object ContractDatabase {
    const val AUTHORITY = "com.puput.consumerapp"
    const val SCHEME = "content"

    internal class FavoriteUser : BaseColumns {

        companion object {
            const val TABLE_NAME = "favorite_user"
            const val _ID = "id"
            const val USERNAME = "login"
            const val AVATAR = "avatar_url"


            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }

    }
}