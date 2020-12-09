package pl.handsome.club.repobrowser.api

interface GitHubApi {

    suspend fun search(partialRepoName: String): List<ApiSearchRepository>

    suspend fun getRepository(ownerId: Long, id: Long): ApiRepositoryDetails

}
