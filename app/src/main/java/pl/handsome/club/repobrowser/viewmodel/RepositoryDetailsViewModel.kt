package pl.handsome.club.repobrowser.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import pl.handsome.club.repobrowser.domain.search.SearchRepository
import pl.handsome.club.repobrowser.repository.GithubRepository


@ExperimentalCoroutinesApi
class RepositoryDetailsViewModel(
    private val githubRepository: GithubRepository
) : ViewModel() {

    private val repositoryToLoad = MutableLiveData<SearchRepository>()

    val getRepositoryDetailsState = repositoryToLoad.switchMap {
        liveData {
            emitSource(
                githubRepository.getDetails(it.ownerId, it.id)
            )
        }
    }

    val lastCommits = repositoryToLoad.switchMap {
        liveData {
            emitSource(
                githubRepository.getLastCommits(it.ownerId, it.id, LAST_COMMITS_COUNT)
            )
        }
    }

    fun getDetails(searchRepository: SearchRepository) {
        repositoryToLoad.value = searchRepository
    }

    companion object {
        const val LAST_COMMITS_COUNT = 3
    }

}