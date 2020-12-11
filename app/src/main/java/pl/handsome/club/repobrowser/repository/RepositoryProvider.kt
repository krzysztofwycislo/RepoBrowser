package pl.handsome.club.repobrowser.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import pl.handsome.club.repobrowser.api.ApiProvider
import pl.handsome.club.repobrowser.util.AppCoroutineDispatchers

object RepositoryProvider {

    @ExperimentalCoroutinesApi
    fun createGithubRepository(): GithubRepository {
        val dispatchers = AppCoroutineDispatchers()
        val githubApi = ApiProvider.createGithubApi()

        return GithubRepository(githubApi, dispatchers)
    }

}