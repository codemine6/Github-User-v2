package com.app.githubuser.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.app.githubuser.R
import com.app.githubuser.data.UserItem
import com.app.githubuser.databinding.ItemUserBinding
import com.app.githubuser.fragments.UserDetailFragment
import com.bumptech.glide.Glide

class UserAdapter(
    private val activity: FragmentActivity,
    private val listUser: List<UserItem>
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = listUser.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listUser[position]

        holder.binding.apply {
            Glide.with(holder.itemView.context)
                .load(user.avatarUrl)
                .into(civAvatar)

            tvLogin.text = user.login
        }

        holder.itemView.setOnClickListener {
            val fragment = UserDetailFragment()
            fragment.arguments = Bundle().apply {
                putString("LOGIN", user.login)
            }

            activity.supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameContainer, fragment)
                addToBackStack(null)
                commit()
            }
        }
    }
}