package pl.handsome.club.repobrowser.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import pl.handsome.club.repobrowser.repository.GithubRepository


@ExperimentalCoroutinesApi
class SearchRepositoryViewModel(
    private val githubRepository: GithubRepository
) : ViewModel() {

    private val partialRepoName = MutableLiveData<String>()

    val searchGitRepositoriesState = partialRepoName.switchMap {
        liveData { emitSource(githubRepository.search(it)) }
    }

    fun search(name: String) {
        if (name.isEmpty() || name == partialRepoName.value)
            return

        partialRepoName.value = name
    }

}