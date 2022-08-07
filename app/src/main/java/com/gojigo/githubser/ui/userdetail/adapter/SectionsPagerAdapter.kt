package com.gojigo.githubser.ui.userdetail.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gojigo.githubser.ui.userdetail.UserListFragment

class SectionsPagerAdapter(activity: AppCompatActivity, private val userName: String) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return UserListFragment.newInstance(userName, position)
    }
}