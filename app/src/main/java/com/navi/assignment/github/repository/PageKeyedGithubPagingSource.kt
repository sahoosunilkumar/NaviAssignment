package com.navi.assignment.github.repository

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadParams.Append
import androidx.paging.PagingSource.LoadResult.Page
import androidx.paging.PagingState
import com.navi.assignment.github.api.GithubApi
import com.navi.assignment.github.model.PullInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class PageKeyedGithubPagingSource(private val dispatcher: CoroutineDispatcher,
        private val githubApi: GithubApi,
        private val owner: String,
        private val repo: String,
        private val state: String,
) : PagingSource<String, PullInfo>() {
    override suspend fun load(params: LoadParams<String>): LoadResult<String, PullInfo> {
        return try {
            withContext(dispatcher) {
                val data = githubApi.getPullRequests(
                        owner = owner,
                        repo = repo,
                        state = state,
                        page = if (params is Append) (params.key.toIntOrNull()
                                ?: 1).toString() else "1",
                        limit = params.loadSize
                )

                Page(
                        data = data,
                        prevKey = params.key,
                        nextKey = ((params.key?.toIntOrNull() ?: 1) + 1).toString()
                )
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, PullInfo>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}
