package pl.handsome.club.repobrowser.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import pl.handsome.club.repobrowser.repository.RepositoryProvider

// we can replace that part with some DI library, but in that app it would be overkill
@ExperimentalCoroutinesApi
object ViewModelFactory : ViewModelProvider.Factory {

    private val githubRepository = RepositoryProvider.createGithubRepository()


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            SearchRepositoryViewModel::class.java -> SearchRepositoryViewModel(githubRepository)
            RepositoryDetailsViewModel::class.java -> RepositoryDetailsViewModel(githubRepository)
            else -> modelClass.newInstance()
        }

        return viewModel as T
    }


}
