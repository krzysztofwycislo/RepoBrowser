package pl.handsome.club.repobrowser.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import pl.handsome.club.repobrowser.api.ApiSearchRepository
import pl.handsome.club.repobrowser.domain.SearchRepositoriesState
import pl.handsome.club.repobrowser.api.GitHubApi
import pl.handsome.club.repobrowser.api.toDomain
import pl.handsome.club.repobrowser.util.AppCoroutineDispatchers


@ExperimentalCoroutinesApi
class GithubRepository(
    private val gitHubApi: GitHubApi,
    private val dispatchers: AppCoroutineDispatchers
) {

    fun search(partialRepoName: String): Flow<SearchRepositoriesState> {
        return flow<SearchRepositoriesState> {
            val repositories = searchOnApi(partialRepoName)
            emit(SearchRepositoriesState.Success(repositories))
        }
            .onStart { emit(SearchRepositoriesState.InProgress) }
            .catch { emit(SearchRepositoriesState.Error(it)) }
            .flowOn(dispatchers.io)
    }

    private suspend fun searchOnApi(partialRepoName: String) =
        gitHubApi.search(partialRepoName).map(ApiSearchRepository::toDomain)


}
