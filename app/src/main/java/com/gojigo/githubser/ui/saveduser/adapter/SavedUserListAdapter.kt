package com.gojigo.githubser.ui.saveduser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.gojigo.githubser.data.local.entity.UserEntity
import com.gojigo.githubser.databinding.ItemListUsersBinding
import com.gojigo.githubser.ui.main.adapter.UserListViewHolder

class SavedUserListAdapter(private val onItemClick: (UserEntity) -> Unit) :
    ListAdapter<UserEntity, UserListViewHolder>(
        DIFF_CALLBACK
    ) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val binding =
            ItemListUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserListViewHolder(binding) { pos ->
            onItemClick(getItem(pos))
        }
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<UserEntity> by lazy {
            object : DiffUtil.ItemCallback<UserEntity>() {
                override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                    return oldItem == newItem
                }

            }
        }
    }

}