package com.app.githubuser.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.githubuser.fragments.UserFollowFragment

class UserFollowAdapter(
    fragment: Fragment,
    private val login: String
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = UserFollowFragment()
        fragment.arguments = Bundle().apply {
            putInt("TYPE", position)
            putString("LOGIN", login)
        }
        return fragment
    }
}