package com.puput.github

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.puput.github.databinding.ActivityMainBinding
import com.puput.github.detailuser.UserDetailActivity
import com.puput.github.favorite.FavoriteUserActivity
import com.puput.github.model.UserList
import com.puput.github.settings.SettingsAlarmActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var userAdapter: UserAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ViewModelMain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userAdapter = UserAdapter()

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(ViewModelMain::class.java)


        binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.recyclerView.setHasFixedSize(true)


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.setSearchData(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.length!! > 0) searchUser(newText.toString())
                return false
            }
        })

        binding.btnFav.setOnClickListener(this)
        binding.settingsAlarm.setOnClickListener(this)
    }

    private fun searchUser(username: String) {

        binding.progressBar.visibility = View.VISIBLE
        viewModel.setSearchData(username)
        viewModel.getSearchData().observe(this) {
            userAdapter.setDataList(it)
            if (it.count() > 0) {
                binding.tvFound.visibility = View.INVISIBLE

            } else {
                binding.tvFound.visibility = View.VISIBLE
            }
            Log.d("FOLLOWER", it.count().toString())
            binding.apply {
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                recyclerView.adapter = userAdapter
                userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: UserList) {
                        val intent = Intent(this@MainActivity, UserDetailActivity::class.java)
                        intent.putExtra(UserDetailActivity.EXTRA_USERNAME, data.login)
                        intent.putExtra(UserDetailActivity.EXTRA_AVATAR, data.avatar_url)
                        intent.putExtra(UserDetailActivity.EXTRA_ID, data.id)
                        startActivity(intent)
                    }

                })
            }
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_fav -> {
                startActivity(Intent(this, FavoriteUserActivity::class.java))
            }
            R.id.settings_alarm -> {
                startActivity(Intent(this, SettingsAlarmActivity::class.java))
            }
        }
    }
}

