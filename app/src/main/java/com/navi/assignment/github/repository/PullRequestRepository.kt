package com.navi.assignment.github.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.navi.assignment.github.api.GithubApi
import kotlinx.coroutines.CoroutineDispatcher

class PullRequestRepository(private val githubApi: GithubApi) : GithubRepository {
    override fun getPullRequests(dispatcher: CoroutineDispatcher, owner: String, repo: String, state: String, pageSize: Int) = Pager(
            PagingConfig(pageSize)
    ) {
        PageKeyedGithubPagingSource(
                dispatcher,
                githubApi = githubApi,
                owner = owner,
                repo = repo,
                state = state
        )
    }.flow
}
