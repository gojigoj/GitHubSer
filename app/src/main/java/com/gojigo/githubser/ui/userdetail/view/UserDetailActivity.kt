package com.gojigo.githubser.ui.userdetail.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.gojigo.githubser.R
import com.gojigo.githubser.data.Result
import com.gojigo.githubser.data.local.entity.UserEntity
import com.gojigo.githubser.data.response.UserDetailResponse
import com.gojigo.githubser.databinding.ActivityUserDetailBinding
import com.gojigo.githubser.ui.userdetail.adapter.SectionsPagerAdapter
import com.gojigo.githubser.util.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private val viewModel: UserDetailViewModel by viewModels()
    private val themeViewModel: ThemeViewModel by viewModels()

    private var isSaved: Boolean = false
    private var isDarkModeActive: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<UserEntity>(USER_DATA) as UserEntity

        initToolbar(user)
        binding.btnSaveUser.isEnabled = false
        viewModel.getUserDetail(user.login.toString()).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.pbLoading.show()
                }
                is Result.Success -> {
                    binding.pbLoading.hide()
                    binding.btnSaveUser.isEnabled = true
                    initView(result.data)
                }
                is Result.Error -> {
                    binding.pbLoading.hide()
                    showToast(this, result.errorResponse)
                }
            }
        }
    }

    private fun initView(user: UserDetailResponse) {
        themeViewModel.getThemeSettings().observe(this) { isDark ->
            isDarkModeActive = isDark
            if (isDark) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.apply {
            ivProfilePicture.setRoundImage(user.avatarUrl!!)
            tvRepoNum.text = user.publicRepos.toString()
            tvFollowersNum.text = user.followers.toString()
            tvFollowingNum.text = user.following.toString()
            tvFullName.text = user.name.replaceWhenEmpty()
            tvCompany.text = user.company.replaceWhenEmpty()
            tvLocation.text = user.location.replaceWhenEmpty()
        }
        setBtnListener(user)
        setBtnStyle(user.id)
        initTab(user.login!!)
    }

    private fun setBtnListener(user: UserDetailResponse) {
        val savedUser = UserEntity(
            id = user.id,
            login = user.login,
            avatarUrl = user.avatarUrl,
            createdAt = getDate()
        )
        binding.btnSaveUser.setOnClickListener {
            if (isSaved) {
                viewModel.unSaveUser(savedUser)
            } else {
                viewModel.saveUser(savedUser)
            }
        }
    }

    private fun setBtnStyle(userId: Int) {
        viewModel.isUserSaved(userId).observe(this) { listUser ->
            isSaved = listUser.isNotEmpty()
            if (isSaved) {
                binding.btnSaveUser.isSelected = true
                binding.btnSaveUser.text = resources.getString(R.string.saved)
                val color = if (isDarkModeActive) R.color.blue_light else R.color.blue_primary
                binding.btnSaveUser.setTextColor(ContextCompat.getColor(this, color))
            } else {
                binding.btnSaveUser.isSelected = false
                binding.btnSaveUser.text = resources.getString(R.string.save)
                binding.btnSaveUser.setTextColor(ContextCompat.getColor(this, R.color.white))
            }
        }
    }

    private fun initTab(userName: String) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, userName)
        val viewPager: ViewPager2 = findViewById(R.id.vp_follower)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tab_follower)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun initToolbar(user: UserEntity) {
        supportActionBar?.title = user.login
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }

    companion object {
        private const val USER_DATA = "user_data"

        fun moveToDetailUserActivity(context: Context, user: UserEntity) {
            val intent = Intent(context, UserDetailActivity::class.java)
            intent.putExtra(USER_DATA, user)
            context.startActivity(intent)
        }

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.following,
            R.string.followers
        )
    }
}