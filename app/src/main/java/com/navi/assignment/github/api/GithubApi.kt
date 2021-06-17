package com.navi.assignment.github.api

import com.navi.assignment.github.model.PullInfo
import com.navi.assignment.github.repository.ignoreAllSSLErrors
import okhttp3.ConnectionSpec
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @Headers("Accept:application/vnd.github.v3+json")
    @GET("/repos/{owner}/{repo}/pulls")
    suspend fun getPullRequests(
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Query("per_page") limit: Int,
            @Query("state") state: String,
            @Query("page") page: String? = null
    ): List<PullInfo>

    companion object {
        private const val BASE_URL = "https://api.github.com/"
        fun create(): GithubApi {
            val client = OkHttpClient.Builder().ignoreAllSSLErrors().connectionSpecs(listOf(ConnectionSpec.CLEARTEXT,
                    ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                            .allEnabledTlsVersions()
                            .allEnabledCipherSuites()
                            .build()))
                    .build()
            return Retrofit.Builder()
                    .baseUrl(HttpUrl.parse(BASE_URL)!!)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(GithubApi::class.java)
        }
    }
}