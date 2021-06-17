package com.navi.assignment.github

import com.navi.assignment.github.api.GithubApi
import com.navi.assignment.github.repository.GithubRepository
import com.navi.assignment.github.repository.PullRequestRepository

interface ServiceLocator {
    companion object {
        private val LOCK = Any()
        private var instance: ServiceLocator? = null
        fun instance(): ServiceLocator {
            synchronized(LOCK) {
                if (instance == null) {
                    instance = GithubServiceLocator()
                }
                return instance!!
            }
        }
    }

    fun getRepository(): GithubRepository

    fun getGithubApi(): GithubApi
}

open class GithubServiceLocator() : ServiceLocator {

    private val api by lazy {
        GithubApi.create()
    }

    override fun getRepository(): GithubRepository {
        return PullRequestRepository(
                githubApi = getGithubApi()
        )
    }

    override fun getGithubApi(): GithubApi = api
}