package com.navi.assignment.github.repository

import androidx.paging.PagingSource
import com.navi.assignment.github.BaseTestCase
import com.navi.assignment.github.CoroutineTestRule
import com.navi.assignment.github.MockGithubApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class PageKeyedGithubPagingSourceTest : BaseTestCase() {
    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    private val githubApi = MockGithubApi()

    @Test
    fun pageKeyedGithubPagingSource() {
        coroutinesTestRule.testDispatcher.runBlockingTest {

            val pagingSource = PageKeyedGithubPagingSource(coroutinesTestRule.testDispatcher, githubApi, "owner", "repo", "closed")
            val result = pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                            key = null,
                            loadSize = 2,
                            placeholdersEnabled = false
                    )
            )
            //test next page as 2
            assertEquals(true,
                    result.toString().contains("nextKey=2"))
        }
    }
}