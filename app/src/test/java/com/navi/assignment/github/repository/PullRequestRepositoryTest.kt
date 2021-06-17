package com.navi.assignment.github.repository

import androidx.paging.PagingData
import com.navi.assignment.github.CoroutineTestRule
import com.navi.assignment.github.MockGithubApi
import com.navi.assignment.github.model.PullInfo
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import kotlin.jvm.Throws

@OptIn(ExperimentalCoroutinesApi::class)
class PullRequestRepositoryTest {
    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()
    private lateinit var pullRequestRepository: PullRequestRepository

    @Test
    @Throws(Exception::class)
    fun testGetPullRequests() = runBlockingTest {
        val api = spyk(MockGithubApi())
        pullRequestRepository = PullRequestRepository(api)
        val result: Flow<PagingData<PullInfo>> = pullRequestRepository.getPullRequests(coroutinesTestRule.testDispatcher, "owner", "repo", "state", 0)
        Assert.assertNotEquals(null, result.stateIn(TestCoroutineScope()).value)
    }
}