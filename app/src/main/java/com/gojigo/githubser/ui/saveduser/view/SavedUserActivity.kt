package com.gojigo.githubser.ui.saveduser.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.gojigo.githubser.R
import com.gojigo.githubser.data.local.entity.UserEntity
import com.gojigo.githubser.databinding.FragmentUserListBinding
import com.gojigo.githubser.ui.saveduser.adapter.SavedUserListAdapter
import com.gojigo.githubser.ui.userdetail.view.UserDetailActivity
import com.gojigo.githubser.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedUserActivity : AppCompatActivity() {

    private lateinit var binding: FragmentUserListBinding

    private val viewModel: SavedUserViewModel by viewModels()
    private val themeViewModel: ThemeViewModel by viewModels()

    private val userSavedAdapter: SavedUserListAdapter by lazy {
        SavedUserListAdapter { user ->
            UserDetailActivity.moveToDetailUserActivity(this, user)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initToolbar()
        initRecyclerView()

        viewModel.getAllUser().observe(this) { listUser: List<UserEntity> ->
            if (listUser.isNotEmpty()) {
                binding.pbLoading.hide()
                binding.layoutUserEmpty.rootUserEmpty.hide()
                binding.rvListUser.show()
                userSavedAdapter.submitList(listUser)
            } else {
                binding.pbLoading.hide()
                binding.rvListUser.hide()
                binding.layoutUserEmpty.rootUserEmpty.show()
                showNoSavedLayout()
            }
        }
    }

    private fun initView() {
        themeViewModel.getThemeSettings().observe(this) { isDark ->
            if (isDark) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvListUser.apply {
            layoutManager = LinearLayoutManager(this@SavedUserActivity)
            adapter = userSavedAdapter
            setHasFixedSize(true)
        }
    }

    private fun showNoSavedLayout() {
        binding.layoutUserEmpty.ivEmpty.setImageResource(R.drawable.ic_no_saved_user)
        binding.layoutUserEmpty.tvEmptyDesc.text = resources.getString(R.string.desc_saved_is_empty)
    }

    private fun initToolbar() {
        supportActionBar?.title = resources.getString(R.string.saved)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }

    companion object {
        fun moveToSavedUserActivity(context: Context) {
            val intent = Intent(context, SavedUserActivity::class.java)
            context.startActivity(intent)
        }
    }
}