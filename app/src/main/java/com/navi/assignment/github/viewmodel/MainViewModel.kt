package com.navi.assignment.github.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.navi.assignment.github.ServiceLocator
import com.navi.assignment.github.model.PullInfo
import com.navi.assignment.github.repository.GithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class MainViewModel(private val repository: GithubRepository) : ViewModel() {

    fun getPullRequests(owner: String, repo: String, state: String): Flow<PagingData<PullInfo>> {
        return repository.getPullRequests(Dispatchers.IO, owner, repo, state, 10)
    }
}
