package com.app.githubuser.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.githubuser.data.Repository
import com.app.githubuser.databinding.ItemRepositoryBinding

class RepositoryAdapter(
    private val repositories: List<Repository>
) : RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemRepositoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = repositories.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repository = repositories[position]

        holder.binding.apply {
            tvRepositoryName.text = repository.name
            tvLastUpdate.text = repository.updatedAt
            tvLanguage.text = repository.language
            tvStar.text = repository.stargazersCount.toString()
        }
    }
}