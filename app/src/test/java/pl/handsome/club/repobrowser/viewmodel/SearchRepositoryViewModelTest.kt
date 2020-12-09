package pl.handsome.club.repobrowser.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import pl.handsome.club.repobrowser.api.ApiSearchRepository
import pl.handsome.club.repobrowser.api.GitHubApi
import pl.handsome.club.repobrowser.api.toDomain
import pl.handsome.club.repobrowser.domain.SearchRepositoriesState
import pl.handsome.club.repobrowser.apiSearchRepositoriesExample
import pl.handsome.club.repobrowser.repository.GithubRepository
import pl.handsome.club.repobrowser.rule.CoroutineTestRule
import java.lang.IllegalStateException


@ExperimentalCoroutinesApi
@FlowPreview
internal class SearchRepositoryViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: SearchRepositoryViewModel

    @Mock
    private lateinit var gitHubApi: GitHubApi

    @Mock
    private lateinit var observer: Observer<SearchRepositoriesState>


    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        val githubRepository = GithubRepository(gitHubApi, coroutineTestRule.testCoroutineDispatchers)
        viewModel = SearchRepositoryViewModel(githubRepository)
        viewModel.searchGitRepositoriesState.observeForever(observer)
    }

    @After
    fun cleanup() {
        viewModel.searchGitRepositoriesState.removeObserver(observer)
    }

    @Test
    fun `search for repositories - success`() = coroutineTestRule.runBlockingTest {
        `when`(gitHubApi.search(anyString())).thenReturn(apiSearchRepositoriesExample)

        viewModel.search("test")

        verify(observer).onChanged(SearchRepositoriesState.InProgress)

        val expectedSearchRepositories = apiSearchRepositoriesExample
            .map(ApiSearchRepository::toDomain)

        verify(observer).onChanged(SearchRepositoriesState.Success(expectedSearchRepositories))
    }

    @Test
    fun `search for repositories - empty partial name - nothing should happen`() {
        viewModel.search("")

        verify(observer, never()).onChanged(any())
    }

    @Test
    fun `load git repositories - api error`() = coroutineTestRule.runBlockingTest {
        val exampleException = IllegalStateException()

        `when`(gitHubApi.search(anyString())).thenThrow(exampleException)

        viewModel.search("test")

        verify(observer).onChanged(SearchRepositoriesState.InProgress)
        verify(observer).onChanged(SearchRepositoriesState.Error(exampleException))
    }


}