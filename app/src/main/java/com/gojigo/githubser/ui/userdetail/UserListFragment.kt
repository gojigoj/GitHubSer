package com.gojigo.githubser.ui.userdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gojigo.githubser.R
import com.gojigo.githubser.data.Result
import com.gojigo.githubser.databinding.FragmentUserListBinding
import com.gojigo.githubser.ui.saveduser.adapter.SavedUserListAdapter
import com.gojigo.githubser.ui.userdetail.view.UserDetailActivity
import com.gojigo.githubser.ui.userdetail.view.UserDetailViewModel
import com.gojigo.githubser.util.ViewModelFactory
import com.gojigo.githubser.util.hide
import com.gojigo.githubser.util.show
import com.gojigo.githubser.util.showToast

class UserListFragment : Fragment() {

    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserDetailViewModel by viewModels {
        ViewModelFactory.getInstance(
            requireActivity()
        )
    }

    private val listAdapter: SavedUserListAdapter by lazy {
        SavedUserListAdapter { user ->
            UserDetailActivity.moveToDetailUserActivity(requireContext(), user)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initRecyclerView()

        arguments?.apply {
            val userName = getString(USER_NAME) as String
            val tabPosition = getInt(TAB_POSITION, 0)

            if (tabPosition == TAB_FOLLOWING) {
                viewModel.getUserFollowing(userName).observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Loading -> {
                            binding.pbLoading.show()
                            binding.rvListUser.hide()
                        }
                        is Result.Success -> {
                            binding.pbLoading.hide()
                            if (result.data.isNotEmpty()) {
                                binding.layoutUserEmpty.rootUserEmpty.hide()
                                binding.rvListUser.show()
                                listAdapter.submitList(result.data)
                            } else {
                                binding.rvListUser.hide()
                                showEmptyFollowerLayout(true, tabPosition)
                            }
                        }
                        is Result.Error -> {
                            binding.pbLoading.hide()
                            binding.rvListUser.hide()
                            showEmptyFollowerLayout(false, tabPosition)
                            showToast(requireContext(), result.errorResponse)
                        }
                    }
                }
            } else {
                viewModel.getUserFollowers(userName).observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Loading -> {
                            binding.pbLoading.show()
                            binding.rvListUser.hide()
                        }
                        is Result.Success -> {
                            binding.pbLoading.hide()
                            if (result.data.isNotEmpty()) {
                                binding.layoutUserEmpty.rootUserEmpty.hide()
                                binding.rvListUser.show()
                                listAdapter.submitList(result.data)
                            } else {
                                binding.rvListUser.hide()
                                showEmptyFollowerLayout(true, tabPosition)
                            }
                        }
                        is Result.Error -> {
                            binding.pbLoading.hide()
                            binding.rvListUser.hide()
                            showEmptyFollowerLayout(false, tabPosition)
                            showToast(requireContext(), result.errorResponse)
                        }
                    }
                }
            }
        }


    }

    private fun showEmptyFollowerLayout(isEmpty: Boolean, tabPosition: Int) {
        binding.layoutUserEmpty.rootUserEmpty.show()
        val imageEmpty = if (isEmpty) {
            R.drawable.ic_user_empty
        } else {
            R.drawable.ic_no_saved_user
        }
        binding.layoutUserEmpty.ivEmpty.setImageResource(imageEmpty)
        val additionalText = if (tabPosition == 0) "Followers" else "Following"
        binding.layoutUserEmpty.tvEmptyDesc.text = if (isEmpty) {
            resources.getString(
                R.string.desc_follower_is_empty,
                additionalText
            )
        } else {
            resources.getString(R.string.desc_users_is_not_found)
        }
    }

    private fun initView() {
        binding.pbLoading.show()
        binding.rvListUser.hide()
        binding.layoutUserEmpty.rootUserEmpty.hide()
    }

    private fun initRecyclerView() {
        binding.rvListUser.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listAdapter
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val USER_NAME = "user_name"
        private const val TAB_POSITION = "tab_position"

        private const val TAB_FOLLOWING = 0

        @JvmStatic
        fun newInstance(userName: String, tabPosition: Int) =
            UserListFragment().apply {
                arguments = Bundle().apply {
                    putString(USER_NAME, userName)
                    putInt(TAB_POSITION, tabPosition)
                }
            }
    }
}