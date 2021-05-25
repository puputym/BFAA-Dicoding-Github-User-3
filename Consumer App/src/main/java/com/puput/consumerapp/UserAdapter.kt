package com.puput.consumerapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.puput.consumerapp.databinding.ItemRowUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    var listUser = ArrayList<UserList>()

    fun setDataList(data: ArrayList<UserList>) {
        this.listUser = data
    }

    inner class UserViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UserList) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.avatar_url)
                    .apply(RequestOptions().override(70, 70))
                    .into(tvAvatar)
                tvUsername.text = data.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size
}