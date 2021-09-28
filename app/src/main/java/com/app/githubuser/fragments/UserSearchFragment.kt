package com.app.githubuser.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.githubuser.R
import com.app.githubuser.adapters.UserAdapter
import com.app.githubuser.data.SearchResult
import com.app.githubuser.databinding.FragmentUserSearchBinding
import com.app.githubuser.views.CustomDialog
import com.app.githubuser.viewmodels.UserSearchViewModel

class UserSearchFragment : Fragment() {

    private var _binding: FragmentUserSearchBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserSearchViewModel by activityViewModels()
    private var querySearch: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        activity?.title = getString(R.string.app_name)
        setHasOptionsMenu(true)

        userViewModel.results.observe(viewLifecycleOwner, { showSearchResult(it) })
        userViewModel.isLoading.observe(viewLifecycleOwner, { showLoading(it) })
        userViewModel.isError.observe(viewLifecycleOwner, { error ->
            if (error) {
                CustomDialog().error(requireContext()) {
                    userViewModel.searchUser(querySearch)
                }
            }
        })

        binding.svUsers.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    querySearch = newText
                    userViewModel.searchUser(newText)
                }
                return true
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionSetting -> {
                requireActivity().supportFragmentManager.beginTransaction().apply {
                    replace(R.id.frameContainer, UserSearchFragment())
                    addToBackStack(null)
                    commit()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showSearchResult(results: SearchResult) {
        if (results.totalCount > 0) {
            binding.rvUsers.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = UserAdapter(requireActivity(), results.items)
            }
            binding.rvUsers.isVisible = true
            binding.tvResult.text = getString(R.string.result, results.totalCount)
        } else {
            binding.apply {
                rvUsers.isVisible = false
                tvNotFound.isVisible = true
                tvResult.text = ""
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.apply {
                progressBar.isVisible = true
                sflList.isVisible = true
                tvResult.text = ""
                rvUsers.isVisible = false
                tvNotFound.isVisible = false
            }
        } else {
            binding.apply {
                progressBar.isVisible = false
                sflList.isVisible = false
            }
        }
    }
}