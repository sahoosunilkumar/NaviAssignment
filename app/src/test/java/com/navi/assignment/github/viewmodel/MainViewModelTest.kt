package com.navi.assignment.github.viewmodel

import androidx.paging.PagingData
import com.navi.assignment.github.BaseTestCase
import com.navi.assignment.github.model.PullInfo
import com.navi.assignment.github.repository.GithubRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import org.junit.Test
import kotlin.test.assertEquals

class MainViewModelTest : BaseTestCase() {
    private lateinit var mainViewModel: MainViewModel

    @MockK
    lateinit var repository: GithubRepository

    @MockK
    lateinit var pagingFlowResponse: Flow<PagingData<PullInfo>>

    @Test
    fun testGetPullRequests() {
        mainViewModel = MainViewModel(repository)
        every { repository.getPullRequests(any(), any(), any(), any(), any()) } returns pagingFlowResponse
        val result: Flow<PagingData<PullInfo>> = mainViewModel.getPullRequests("owner", "repo", "state")
        assertEquals(pagingFlowResponse, result)
        verify(exactly = 1) { repository.getPullRequests(any(), "owner", "repo", "state", 10) }
    }
}