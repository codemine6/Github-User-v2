package com.app.githubuser.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.app.githubuser.R
import com.app.githubuser.adapters.UserFollowAdapter
import com.app.githubuser.databinding.FragmentUserFollowBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserFollowFragment : Fragment() {

    private var _binding: FragmentUserFollowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserFollowBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity?.title = getString(R.string.followers)

        arguments?.getString("LOGIN")?.let { login ->
            val userFollowAdapter = UserFollowAdapter(this, login)
            binding.vpFollow.adapter = userFollowAdapter

            TabLayoutMediator(binding.tlFollow, binding.vpFollow) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.followers)
                    1 -> tab.text = getString(R.string.following)
                }
            }.attach()

            binding.tlFollow.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {}
                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    activity?.title = tab?.text
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}