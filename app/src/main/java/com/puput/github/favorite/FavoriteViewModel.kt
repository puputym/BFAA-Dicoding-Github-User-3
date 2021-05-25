package com.puput.github.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.puput.github.database.FavoriteUserDao
import com.puput.github.database.UserDB
import com.puput.github.model.FavoriteUser

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private var userDb: UserDB?
    private var userDao: FavoriteUserDao?

    init {
        userDb = UserDB.getDatabase(application)
        userDao = userDb?.userFavoriteDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>? {
        return userDao?.getFavoriteUser()
    }
}