package com.puput.consumerapp

import android.content.UriMatcher
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.puput.consumerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        const val AUTHORITY = "com.puput.github"
        const val TABLE_NAME = "favorite_user"
        const val LIST = 1
        const val OBJECT = 2
        const val SCHEME = "content"

        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, LIST)
            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", OBJECT)
        }

    }

    private lateinit var userAdapter: UserAdapter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userAdapter = UserAdapter()
        favoriteUser()

    }

    private fun favoriteUser() {
        binding.progressBarFavorite.visibility = View.VISIBLE
        binding.apply {
            rvFavorite.layoutManager = LinearLayoutManager(this@MainActivity)

            val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
            val userList = MappingHelper.mapCursorToArrayList(cursor)
            userAdapter.setDataList(userList)
            rvFavorite.adapter = userAdapter
            Log.d("TEST 2", userList.toString())
        }


        binding.progressBarFavorite.visibility = View.INVISIBLE
    }

}