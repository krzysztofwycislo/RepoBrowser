package pl.handsome.club.repobrowser.api

import pl.handsome.club.repobrowser.api.commit.ApiCommitDetails
import pl.handsome.club.repobrowser.api.repository.details.ApiRepositoryDetails
import pl.handsome.club.repobrowser.api.repository.search.ApiSearchRepository
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {

    @GET("/search/repositories")
    suspend fun search(
        @Query("q") partialRepoName: String,
        @Query("sort") sortBy: String = "stars",
        @Query("order") orderBy: String = "desc"
    ): ApiSearchRepository

    @GET("/repos/{owner}/{repo}")
    suspend fun getRepository(
        @Path("owner") ownerName: String,
        @Path("repo") repoName: String
    ): ApiRepositoryDetails

    @GET("/repos/{owner}/{repo}/commits")
    suspend fun getLastCommits(
        @Path("owner") ownerName: String,
        @Path("repo") repoName: String
    ): List<ApiCommitDetails>

}
