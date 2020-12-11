package pl.handsome.club.repobrowser.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.handsome.club.repobrowser.api.GitHubApi
import pl.handsome.club.repobrowser.repository.GithubRepository
import pl.handsome.club.repobrowser.util.AppCoroutineDispatchers
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


// TODO temporary solution
@ExperimentalCoroutinesApi
object ViewModelFactory : ViewModelProvider.Factory {

    private val githubRepository = createGithubRepository()


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            SearchRepositoryViewModel::class.java -> SearchRepositoryViewModel(githubRepository)
            RepositoryDetailsViewModel::class.java -> RepositoryDetailsViewModel(githubRepository)
            else -> modelClass.newInstance()
        }

        return viewModel as T
    }

    private fun createGithubRepository(): GithubRepository {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        httpClient.addInterceptor(basicAuthInterceptor())

        val dispatchers = AppCoroutineDispatchers()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(httpClient.build())
            .build()

        val gitHubApi = retrofit.create(GitHubApi::class.java)
        return GithubRepository(gitHubApi, dispatchers)
    }

    private fun basicAuthInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request= chain.request()
                .newBuilder()
                .addHeader(
                    "Authorization",
                    "token e358e061071766c549677873020a59cccee7203f"
                )
                .build()

            chain.proceed(request)
        }
    }

}
