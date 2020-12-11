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


    val repositoryDetailsLoadState = repositoryToLoad.switchMap {
        liveData {
            emitSource(
                githubRepository.getDetails(it.ownerName, it.repoName)
            )
        }
    }

    val lastCommitsLoadState = repositoryToLoad.switchMap {
        liveData {
            emitSource(
                githubRepository.getLastCommits(it.ownerName, it.repoName, LAST_COMMITS_COUNT)
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