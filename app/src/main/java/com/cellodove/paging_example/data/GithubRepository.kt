package com.cellodove.paging_example.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cellodove.paging_example.api.GithubService
import com.cellodove.paging_example.model.Repo
import kotlinx.coroutines.flow.Flow

class GithubRepository(private val service: GithubService) {

    fun getSearchResultStream(query : String): Flow<PagingData<Repo>> {
        return Pager(config = PagingConfig(pageSize = NETWORK_PAGE_SIZE,enablePlaceholders = false), pagingSourceFactory = {GithubPagingSource(service,query)}).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 30
    }
}