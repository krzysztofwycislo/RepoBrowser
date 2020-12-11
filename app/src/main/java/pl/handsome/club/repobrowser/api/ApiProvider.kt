package pl.handsome.club.repobrowser.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.handsome.club.repobrowser.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiProvider {

    // that token has only access to public data and operations so it can stay here
    // its only purpose is to increase hourly request limit
    private const val GITHUB_AUTH_TOKEN = "e358e061071766c549677873020a59cccee7203f"
    private const val GITHUB_URL = "https://api.github.com/"

    private val githubRetrofit = createGithubRetrofit()


    fun createGithubApi(): GitHubApi {
        return githubRetrofit.create(GitHubApi::class.java)
    }

    private fun createGithubRetrofit(): Retrofit {
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(basicAuthInterceptor())

        if(BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            httpClient.addInterceptor(logging)
        }

        return Retrofit.Builder()
            .baseUrl(GITHUB_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

    private fun basicAuthInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request= chain.request()
                .newBuilder()
                .addHeader("Authorization", "token $GITHUB_AUTH_TOKEN")
                .build()

            chain.proceed(request)
        }
    }

}