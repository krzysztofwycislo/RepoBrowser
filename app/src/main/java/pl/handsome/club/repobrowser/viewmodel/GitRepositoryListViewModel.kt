package pl.handsome.club.repobrowser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pl.handsome.club.repobrowser.domain.SearchRepositoriesState
import pl.handsome.club.repobrowser.repository.GithubRepository

@ExperimentalCoroutinesApi
class GitRepositoryListViewModel(
    private val githubRepository: GithubRepository
) : ViewModel() {

    private val searchGitRepositoriesState = MutableLiveData<SearchRepositoriesState>()

    fun getSearchGitRepositoriesState(): LiveData<SearchRepositoriesState> =
        searchGitRepositoriesState


    fun search(partialRepoName: String) {
        if(partialRepoName.isEmpty())
            return

        viewModelScope.launch {
            githubRepository.search(partialRepoName)
                .collect(searchGitRepositoriesState::postValue)
        }
    }

}