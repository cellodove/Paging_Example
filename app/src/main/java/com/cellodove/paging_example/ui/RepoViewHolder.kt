package com.cellodove.paging_example.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.RecyclerView
import com.cellodove.paging_example.R
import com.cellodove.paging_example.databinding.RepoViewItemBinding
import com.cellodove.paging_example.model.Repo

class RepoViewHolder(private val binding : RepoViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private var repo: Repo? = null

    init {
        binding.root.setOnClickListener {
            repo?.url?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                binding.root.context.startActivity(intent)
            }
        }
    }

    fun bind(repo: Repo?){
        if (repo == null){
            val resources = itemView.resources
            binding.apply {
                repoName.text = resources.getString(R.string.loading)
                repoDescription.visibility = View.GONE
                repoLanguage.visibility = View.GONE
                repoStars.text = resources.getString(R.string.unknown)
                repoForks.text = resources.getString(R.string.unknown)
            }
        }else{
            showRepoData(repo)
        }
    }

    private fun showRepoData(repo: Repo){
        this.repo = repo
        binding.repoName.text = repo.fullName

        var descriptionVisibility = View.GONE
        if (repo.description != null){
            binding.repoDescription.text = repo.description
            descriptionVisibility = View.VISIBLE
        }
        binding.repoDescription.visibility = descriptionVisibility

        binding.repoStars.text = repo.stars.toString()
        binding.repoForks.text = repo.forks.toString()

        var languageVisibility = View.GONE
        if (!repo.language.isNullOrEmpty()) {
            val resources = this.itemView.context.resources
            binding.repoLanguage.text = resources.getString(R.string.language, repo.language)
            languageVisibility = View.VISIBLE
        }
        binding.repoLanguage.visibility = languageVisibility
    }

    companion object{
        fun create(parent: ViewGroup): RepoViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            return RepoViewHolder(RepoViewItemBinding.inflate(layoutInflater))
        }
    }
}