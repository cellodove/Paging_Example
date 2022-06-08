package com.cellodove.paging_example.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.cellodove.paging_example.Injection
import com.cellodove.paging_example.databinding.ActivitySearchRepositoriesBinding
import com.cellodove.paging_example.model.Repo
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchRepositoriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySearchRepositoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this,Injection.provideViewModelFactory(owner = this))[SearchRepositoriesViewModel::class.java]
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(decoration)

        val repoAdapter = ReposAdapter()
        binding.list.adapter = repoAdapter

        lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest { pagingData ->
                repoAdapter.submitData(pagingData)
            }
        }

        repoAdapter.addLoadStateListener { combinedLoadStates ->
            binding.progressBar.isVisible = combinedLoadStates.source.refresh is LoadState.Loading
        }
    }
}