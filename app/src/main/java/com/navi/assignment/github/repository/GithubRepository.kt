package com.navi.assignment.github.repository

import androidx.paging.PagingData
import com.navi.assignment.github.model.PullInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    fun getPullRequests(dispatcher: CoroutineDispatcher, owner: String, repo: String, state: String, pageSize: Int): Flow<PagingData<PullInfo>>
}