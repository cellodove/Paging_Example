package com.cellodove.paging_example.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cellodove.paging_example.data.GithubRepository
import com.cellodove.paging_example.model.Repo
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchRepositoriesViewModel(
    private val repository: GithubRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var pagingDataFlow: Flow<PagingData<Repo>>

    init {
        pagingDataFlow = initSearchRepo(queryString = "android")
    }

    private fun initSearchRepo(queryString: String): Flow<PagingData<Repo>> = repository.getSearchResultStream(queryString)

    fun searchRepo(queryString: String) {
        pagingDataFlow = repository.getSearchResultStream(queryString)
    }

}
