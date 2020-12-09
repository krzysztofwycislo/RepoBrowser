package pl.handsome.club.repobrowser.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import pl.handsome.club.repobrowser.api.ApiRepositoryDetails
import pl.handsome.club.repobrowser.api.ApiSearchRepository
import pl.handsome.club.repobrowser.api.GitHubApi
import pl.handsome.club.repobrowser.api.toDomain
import pl.handsome.club.repobrowser.domain.*
import pl.handsome.club.repobrowser.domain.details.GetRepositoryDetailsState
import pl.handsome.club.repobrowser.domain.search.SearchRepositoriesState
import pl.handsome.club.repobrowser.util.AppCoroutineDispatchers


@ExperimentalCoroutinesApi
class GithubRepository(
    private val gitHubApi: GitHubApi,
    private val dispatchers: AppCoroutineDispatchers
) {

    fun search(partialRepoName: String): LiveData<SearchRepositoriesState> {
        return flow<SearchRepositoriesState> {
            val result = gitHubApi.search(partialRepoName)
                .map(ApiSearchRepository::toDomain)
                .let(SearchRepositoriesState::Success)

            emit(result)
        }
            .onStart { emit(SearchRepositoriesState.InProgress) }
            .catch { emit(SearchRepositoriesState.Error(it)) }
            .flowOn(dispatchers.io)
            .asLiveData()
    }

    fun getDetails(ownerId: Long, id: Long): LiveData<GetRepositoryDetailsState> {
        return flow<GetRepositoryDetailsState> {
            val result = gitHubApi.getRepository(ownerId, id)
                .let(ApiRepositoryDetails::toDomain)
                .let(GetRepositoryDetailsState::Success)

            emit(result)
        }
            .onStart { emit(GetRepositoryDetailsState.InProgress) }
            .catch { emit(GetRepositoryDetailsState.Error(it)) }
            .flowOn(dispatchers.io)
            .asLiveData()
    }

}
