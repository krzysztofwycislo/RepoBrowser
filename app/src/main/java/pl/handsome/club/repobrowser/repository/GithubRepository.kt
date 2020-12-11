package pl.handsome.club.repobrowser.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import pl.handsome.club.repobrowser.api.*
import pl.handsome.club.repobrowser.api.commit.ApiCommitDetails
import pl.handsome.club.repobrowser.api.details.ApiRepositoryDetails
import pl.handsome.club.repobrowser.api.search.ApiSearchRepository
import pl.handsome.club.repobrowser.domain.details.GetCommitsDetailsState
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
                .let(ApiSearchRepository::toDomain)
                .let(SearchRepositoriesState::Success)

            emit(result)
        }
            .onStart { emit(SearchRepositoriesState.InProgress) }
            .catch { emit(SearchRepositoriesState.Error(it)) }
            .flowOn(dispatchers.io)
            .asLiveData()
    }

    fun getDetails(ownerName: String, repoName: String): LiveData<GetRepositoryDetailsState> {
        return flow<GetRepositoryDetailsState> {
            val result = gitHubApi.getRepository(ownerName, repoName)
                .let(ApiRepositoryDetails::toDomain)
                .let(GetRepositoryDetailsState::Success)

            emit(result)
        }
            .onStart { emit(GetRepositoryDetailsState.InProgress) }
            .catch { emit(GetRepositoryDetailsState.Error(it)) }
            .flowOn(dispatchers.io)
            .asLiveData()
    }

    fun getLastCommits(
        ownerName: String,
        repoName: String,
        commitsCount: Int
    ): LiveData<GetCommitsDetailsState> {
        return flow<GetCommitsDetailsState> {
            val result = gitHubApi.getLastCommits(ownerName, repoName)
                .take(commitsCount)
                .map(ApiCommitDetails::toDomain)
                .let(GetCommitsDetailsState::Success)

            emit(result)
        }
            .onStart { emit(GetCommitsDetailsState.InProgress) }
            .catch { emit(GetCommitsDetailsState.Error(it)) }
            .flowOn(dispatchers.io)
            .asLiveData()
    }

}
