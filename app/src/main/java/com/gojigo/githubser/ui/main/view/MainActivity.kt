package com.gojigo.githubser.ui.main.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.gojigo.githubser.R
import com.gojigo.githubser.data.Result
import com.gojigo.githubser.databinding.ActivityMainBinding
import com.gojigo.githubser.ui.main.adapter.UserListAdapter
import com.gojigo.githubser.ui.saveduser.view.SavedUserActivity
import com.gojigo.githubser.ui.userdetail.view.UserDetailActivity
import com.gojigo.githubser.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val themeViewModel: ThemeViewModel by viewModels()

    private val usersAdapter: UserListAdapter by lazy {
        UserListAdapter { user ->
            UserDetailActivity.moveToDetailUserActivity(this@MainActivity, user)
        }
    }
    private var isDarkModeActive = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        splashScreen.apply {
            setKeepOnScreenCondition { viewModel.isLoading.value!! }
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initRecyclerView()
        initSearch()

        viewModel._listUser.observe(this) { listUser ->
            binding.pbLoading.hide()
            if (!listUser.isNullOrEmpty()) {
                binding.layoutUserEmpty.rootUserEmpty.hide()
                binding.rvListUser.show()
                usersAdapter.setListUsers(listUser)
            }
        }
    }

    private fun initView() {
        themeViewModel.getThemeSettings().observe(this) { isDark ->
            isDarkModeActive = isDark
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        supportActionBar?.hide()

        binding.pbLoading.hide()
        binding.rvListUser.hide()
        binding.layoutUserEmpty.rootUserEmpty.show()

        binding.btnSavedUser.setOnClickListener {
            SavedUserActivity.moveToSavedUserActivity(this)
        }

        binding.btnThemeMode.setOnClickListener {
            themeViewModel.saveThemeSettings(!isDarkModeActive)
        }
    }

    private fun initRecyclerView() {
        binding.rvListUser.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = usersAdapter
            setHasFixedSize(true)
        }
    }

    private fun initSearch() {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        binding.svGithubUsers.apply {
            maxWidth = Integer.MAX_VALUE
            setIconifiedByDefault(false)
            onActionViewExpanded()
            setTypeFace(context)
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            queryHint = resources.getString(R.string.search_query_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    val userName = query?.trim()
                    if (!userName.isNullOrEmpty()) {
                        viewModel.searchUser(userName).observe(this@MainActivity) { result ->
                            when (result) {
                                is Result.Loading -> {
                                    binding.pbLoading.show()
                                    binding.rvListUser.hide()
                                    binding.layoutUserEmpty.rootUserEmpty.hide()
                                }
                                is Result.Success -> {
                                    binding.pbLoading.hide()
                                    if (result.data.isNotEmpty()) {
                                        binding.layoutUserEmpty.rootUserEmpty.hide()
                                        binding.rvListUser.show()
                                        usersAdapter.setListUsers(result.data)
                                        viewModel._listUser.value = result.data
                                    } else {
                                        binding.rvListUser.hide()
                                        showEmptyUserNotFound()
                                    }
                                }
                                is Result.Error -> {
                                    binding.pbLoading.hide()
                                    binding.rvListUser.hide()
                                    showEmptyUserNotFound()
                                    showToast(this@MainActivity, result.errorResponse)
                                }
                            }
                        }
                        binding.svGithubUsers.clearFocus()
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean = false

            })
        }

    }

    private fun showEmptyUserNotFound() {
        binding.apply {
            layoutUserEmpty.rootUserEmpty.show()
            layoutUserEmpty.ivEmpty.setImageResource(R.drawable.ic_search_not_found)
            layoutUserEmpty.tvEmptyDesc.text =
                resources.getString(R.string.desc_users_is_not_found)
        }
    }
}