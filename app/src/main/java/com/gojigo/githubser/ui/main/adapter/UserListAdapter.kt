package com.gojigo.githubser.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gojigo.githubser.data.local.entity.UserEntity
import com.gojigo.githubser.databinding.ItemListUsersBinding

class UserListAdapter(private val onItemClick: (UserEntity) -> Unit) :
    RecyclerView.Adapter<UserListViewHolder>() {

    private val listUsers = ArrayList<UserEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun setListUsers(listUsers: List<UserEntity>) {
        if (this.listUsers.size > 0) {
            this.listUsers.clear()
        }
        this.listUsers.addAll(listUsers)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val binding =
            ItemListUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserListViewHolder(binding) { pos ->
            onItemClick(listUsers[pos])
        }
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind(listUsers[position])
    }

    override fun getItemCount(): Int = listUsers.size
}