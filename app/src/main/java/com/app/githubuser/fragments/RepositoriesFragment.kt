package com.app.githubuser.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.githubuser.R
import com.app.githubuser.adapters.RepositoryAdapter
import com.app.githubuser.data.Repository
import com.app.githubuser.databinding.FragmentRepositoriesBinding
import com.app.githubuser.viewmodels.UserDetailViewModel

class RepositoriesFragment : Fragment() {

    private var _binding: FragmentRepositoriesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepositoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity?.title = getString(R.string.repositories)

        viewModel.repositories.observe(viewLifecycleOwner, { showRepositories(it) })

        arguments?.getString("LOGIN")?.let { login ->
            viewModel.getUserRepositories(login)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showRepositories(repositories: List<Repository>) {
        binding.rvRepositories.apply {
            adapter = RepositoryAdapter(repositories)
            layoutManager = LinearLayoutManager(activity)
        }
    }
}