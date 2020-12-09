package pl.handsome.club.repobrowser.api

import pl.handsome.club.repobrowser.api.commit.ApiCommitDetails
import pl.handsome.club.repobrowser.api.details.ApiRepositoryDetails
import pl.handsome.club.repobrowser.api.search.ApiSearchRepository
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {

    @GET("/search/repositories")
    suspend fun search(@Query("") partialRepoName: String): ApiSearchRepository

    @GET("/repos/{owner}/{repo}")
    suspend fun getRepository(
        @Path("owner") ownerId: Long,
        @Path("repo") repoId: Long
    ): ApiRepositoryDetails

    @GET("/repos/{owner}/{repo}/commits")
    suspend fun getLastCommits(
        @Path("owner") ownerId: Long,
        @Path("repo") id: Long
    ): List<ApiCommitDetails>

}
