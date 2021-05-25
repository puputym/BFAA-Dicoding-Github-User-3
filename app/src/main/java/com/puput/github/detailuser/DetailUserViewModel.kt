package com.puput.github.detailuser

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.puput.github.database.FavoriteUserDao
import com.puput.github.database.UserDB
import com.puput.github.model.DetailUserModel
import com.puput.github.model.FavoriteUser
import com.puput.github.model.UserList
import com.puput.github.retrofit.RetrofitAPI
import com.puput.github.retrofit.ServiceAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    val detailUser = MutableLiveData<DetailUserModel>()
    var listFollowers = MutableLiveData<ArrayList<UserList>>()
    var listFollowings = MutableLiveData<ArrayList<UserList>>()

    var followers: LiveData<ArrayList<UserList>> = listFollowers
    var following: LiveData<ArrayList<UserList>> = listFollowings
    private var userDb: UserDB?
    private var userDao: FavoriteUserDao?

    fun getUserDetail(): MutableLiveData<DetailUserModel> {
        return detailUser
    }

    init {
        userDb = UserDB.getDatabase(application)
        userDao = userDb?.userFavoriteDao()
    }

    fun setDetailUser(username: String) {
        setListFollowers(username)
        setListFollowings(username)
        val retrofitAPI = RetrofitAPI.getRetrofitAPI().create(ServiceAPI::class.java)
        val call = retrofitAPI.getUserDetail(username)
        call.enqueue(object : Callback<DetailUserModel?> {
            override fun onResponse(
                call: Call<DetailUserModel?>,
                response: Response<DetailUserModel?>
            ) {
                if (response.isSuccessful) {
                    detailUser.postValue(response.body())
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<DetailUserModel?>, t: Throwable) {
                Log.d("Failure", t.message.toString())

            }
        })
    }


    private fun setListFollowers(username: String) {
        val retrofitAPI = RetrofitAPI.getRetrofitAPI().create(ServiceAPI::class.java)
        val call = retrofitAPI.getUserFollowers(username)
        call.enqueue(object : Callback<ArrayList<UserList>?> {
            override fun onResponse(
                call: Call<ArrayList<UserList>?>,
                response: Response<ArrayList<UserList>?>
            ) {
                if (response.isSuccessful) {
                    listFollowers.postValue(response.body())
                }
                Log.d("FOLLOWERSFRAGMENT", listFollowers.toString())
            }

            override fun onFailure(call: Call<ArrayList<UserList>?>, t: Throwable) {
                Log.d("FOLLOWERSFRAGMENT", t.message.toString())
            }
        })
    }

    private fun setListFollowings(username: String) {
        val retrofitAPI = RetrofitAPI.getRetrofitAPI().create(ServiceAPI::class.java)
        val call = retrofitAPI.getUserFollowings(username)
        call.enqueue(object : Callback<ArrayList<UserList>?> {
            override fun onResponse(
                call: Call<ArrayList<UserList>?>,
                response: Response<ArrayList<UserList>?>
            ) {
                listFollowings.postValue(response.body())
                Log.d("FOLLOWINGSFRAGMENT", listFollowings.toString())
            }

            override fun onFailure(call: Call<ArrayList<UserList>?>, t: Throwable) {
                Log.d("FOLLOWINGFRAGMENT", t.message.toString())
            }
        })
    }

    fun addUserFavorite(username: String, avatarUrl: String, id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                username,
                avatarUrl,
                id
            )
            userDao?.addUserFavorite(user)
        }

    }

    suspend fun cekFavoriteUser2(login: String) = userDao?.cekFavoriteUser2(login)

    fun deleteFromFavoriteUser2(username: String) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.deleteFromFavoriteUser2(username)
        }
    }
}