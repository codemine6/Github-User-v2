package com.app.githubuser.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.app.githubuser.R
import com.app.githubuser.adapters.RepositoryAdapter
import com.app.githubuser.adapters.UserFollowAdapter
import com.app.githubuser.data.Repository
import com.app.githubuser.data.UserDetail
import com.app.githubuser.databinding.FragmentUserDetailBinding
import com.app.githubuser.views.CustomDialog
import com.app.githubuser.viewmodels.UserDetailViewModel
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailFragment : Fragment() {

    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity?.title = getString(R.string.profile)

        viewModel.user.observe(viewLifecycleOwner, { showUserInfo(it) })
        viewModel.repositories.observe(viewLifecycleOwner, { showRepositories(it)})
        viewModel.isLoading.observe(viewLifecycleOwner, {
            binding.progressBar.isVisible = it
        })
        viewModel.isError.observe(viewLifecycleOwner, { error ->
            if (error) {
                CustomDialog().error(requireActivity())
            }
        })

        arguments?.getString("LOGIN")?.let { login ->
            viewModel.getUser(login)
            viewModel.getUserRepositories(login)

            val userFollowAdapter = UserFollowAdapter(this, login)
            binding.vpFollow.adapter = userFollowAdapter

            TabLayoutMediator(binding.tlFollow, binding.vpFollow) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.followers)
                    1 -> tab.text = getString(R.string.following)
                }
            }.attach()

            binding.btnShowFollow.setOnClickListener {
                val fragment = UserFollowFragment()
                fragment.arguments = Bundle().apply {
                    putString("LOGIN", login)
                }

                requireActivity().supportFragmentManager.beginTransaction().apply {
                    replace(R.id.frameContainer, fragment)
                    addToBackStack(null)
                    commit()
                }
            }

            binding.btnShowRepositories.setOnClickListener {
                val fragment = RepositoriesFragment()
                fragment.arguments = Bundle().apply {
                    putString("LOGIN", login)
                }

                requireActivity().supportFragmentManager.beginTransaction().apply {
                    replace(R.id.frameContainer, fragment)
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        requireActivity().viewModelStore.clear()
    }

    private fun showUserInfo(user: UserDetail) {
        binding.apply {
            civAvatar.isVisible = true
            Glide.with(requireActivity())
                .load(user.avatarUrl)
                .into(civAvatar)

            tvLogin.isVisible = true
            tvLogin.text = user.login

            tlFollow.isVisible = true
            smDetail.root.isVisible = false
        }
    }

    private fun showRepositories(repositories: List<Repository>) {
        binding.tvRepositoryTitle.text = getString(R.string.repository_title, repositories.size)
        binding.rvRepositories.apply {
            adapter = RepositoryAdapter(repositories, 500)
            layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.HORIZONTAL, false)
        }
    }
}