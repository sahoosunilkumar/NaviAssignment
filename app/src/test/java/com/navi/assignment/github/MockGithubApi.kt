package com.navi.assignment.github

import com.navi.assignment.github.api.GithubApi
import com.navi.assignment.github.model.PullInfo

class MockGithubApi : GithubApi {
    override suspend fun getPullRequests(owner: String, repo: String, limit: Int, state: String, page: String?): List<PullInfo> {
        val info1 = PullInfo()
        info1.id = 1
        val info2 = PullInfo()
        info2.id = 1
        return listOf(info1, info2)
    }

}