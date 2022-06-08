package com.cellodove.paging_example.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cellodove.paging_example.api.GithubService
import com.cellodove.paging_example.api.IN_QUALIFIER
import com.cellodove.paging_example.data.GithubRepository.Companion.NETWORK_PAGE_SIZE
import com.cellodove.paging_example.model.Repo
import retrofit2.HttpException
import java.io.IOException

private const val GITHUB_STARTING_PAGE_INDEX = 1

class GithubPagingSource(
    private val service: GithubService,
    private val query: String
) : PagingSource<Int,Repo>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        val apiQuery = query + IN_QUALIFIER

        return try {
            val response = service.searchRepos(apiQuery,position,params.loadSize)
            val repos = response.items
            val nextKey = if (repos.isEmpty()){
                null
            }else{
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(data = repos, prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1, nextKey = nextKey)
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}