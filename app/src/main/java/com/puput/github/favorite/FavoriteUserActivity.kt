package com.puput.github.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.puput.github.R
import com.puput.github.UserAdapter
import com.puput.github.databinding.ActivityFavoriteUserBinding
import com.puput.github.detailuser.UserDetailActivity
import com.puput.github.model.FavoriteUser
import com.puput.github.model.UserList

class FavoriteUserActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var userAdapter: UserAdapter
    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var viewModel: FavoriteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userAdapter = UserAdapter()

        viewModel = ViewModelProvider(
            this,
        ).get(FavoriteViewModel::class.java)
        favoriteUser()
        binding.tvFavorite.setOnClickListener(this)
    }

    private fun favoriteUser() {
        binding.progressBarFavorite.visibility = View.VISIBLE
        viewModel.getFavoriteUser()?.observe(this) {
            if (it != null) {
                val list = mapList(it)
                userAdapter.setDataList(list)
            }
            binding.apply {
                rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteUserActivity)
                rvFavorite.adapter = userAdapter
                userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: UserList) {
                        val intent =
                            Intent(this@FavoriteUserActivity, UserDetailActivity::class.java)
                        intent.putExtra(UserDetailActivity.EXTRA_USERNAME, data.login)
                        intent.putExtra(UserDetailActivity.EXTRA_AVATAR, data.avatar_url)
                        intent.putExtra(UserDetailActivity.EXTRA_ID, data.id)
                        startActivity(intent)
                    }

                })
            }
            binding.progressBarFavorite.visibility = View.INVISIBLE
        }
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<UserList> {
        val userList = ArrayList<UserList>()
        for (user in users) {
            val userMapped = UserList(
                user.login,
                user.id,
                user.avatar_url
            )
            userList.add(userMapped)
        }
        return userList
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.tv_favorite -> finish()
        }
    }
}