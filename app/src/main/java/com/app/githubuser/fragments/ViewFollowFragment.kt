package com.app.githubuser.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.githubuser.adapters.UserAdapter
import com.app.githubuser.data.UserItem
import com.app.githubuser.databinding.FragmentViewFollowBinding
import com.app.githubuser.viewmodels.UserDetailViewModel

class ViewFollowFragment : Fragment() {

    private var _binding: FragmentViewFollowBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserDetailViewModel by activityViewModels()
    private var listType: Int? = 0
    private var userLogin: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.following.observe(viewLifecycleOwner, { showUserFollow(it) })
        viewModel.followers.observe(viewLifecycleOwner, { showUserFollow(it) })

        listType = arguments?.getInt("TYPE")
        userLogin = arguments?.getString("LOGIN")

        getUserFollow(listType, userLogin)
    }

    override fun onResume() {
        super.onResume()
        getUserFollow(listType, userLogin)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getUserFollow(type: Int?, login: String?) {
        if (type != null && login != null) {
            when (type) {
                0 -> viewModel.getUserFollowers(login)
                1 -> viewModel.getUserFollowing(login)
            }
            binding.apply {
                rvFollow.isVisible = false
                tvNotFound.isVisible = false
                sflList.isVisible = true
            }
        }
    }

    private fun showUserFollow(users: List<UserItem>) {
        if (users.isNotEmpty()) {
            binding.rvFollow.apply {
                adapter = UserAdapter(requireActivity(), users)
                layoutManager = LinearLayoutManager(activity)
            }
            binding.rvFollow.isVisible = true
            binding.sflList.isVisible = false
        } else {
            binding.apply {
                tvNotFound.isVisible = true
                sflList.isVisible = false
            }
        }
    }
}