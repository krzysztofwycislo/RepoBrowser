package pl.handsome.club.repobrowser.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import pl.handsome.club.repobrowser.api.GitHubApi
import pl.handsome.club.repobrowser.repository.GithubRepository
import pl.handsome.club.repobrowser.util.AppCoroutineDispatchers
import retrofit2.Retrofit


// TODO temporary solution
@ExperimentalCoroutinesApi
object ViewModelFactory : ViewModelProvider.Factory {

    private val githubRepository = createGithubRepository()


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = when(modelClass) {
            SearchRepositoryViewModel::class.java -> SearchRepositoryViewModel(githubRepository)
            RepositoryDetailsViewModel::class.java -> RepositoryDetailsViewModel(githubRepository)
            else -> modelClass.newInstance()
        }

        return viewModel as T
    }

    private fun createGithubRepository(): GithubRepository {
        val dispatchers = AppCoroutineDispatchers()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .build();

        val gitHubApi = retrofit.create(GitHubApi::class.java)
        return GithubRepository(gitHubApi, dispatchers)
    }

}