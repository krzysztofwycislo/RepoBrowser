package pl.handsome.club.repobrowser.api

interface GitHubApi {

    suspend fun search(partialRepoName: String): List<ApiSearchRepository>

}
