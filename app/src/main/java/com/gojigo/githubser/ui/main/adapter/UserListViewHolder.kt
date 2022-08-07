package com.gojigo.githubser.ui.main.adapter

import androidx.recyclerview.widget.RecyclerView
import com.gojigo.githubser.data.local.entity.UserEntity
import com.gojigo.githubser.databinding.ItemListUsersBinding
import com.gojigo.githubser.util.setRoundImage

class UserListViewHolder(
    private val binding: ItemListUsersBinding,
    private val onItemClickPos: (Int) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnClickListener {
            onItemClickPos(absoluteAdapterPosition)
        }
    }

    fun bind(user: UserEntity) {
        with(binding) {
            ivProfilePicture.setRoundImage(user.avatarUrl!!)
            tvUserName.text = user.login
        }
    }
}