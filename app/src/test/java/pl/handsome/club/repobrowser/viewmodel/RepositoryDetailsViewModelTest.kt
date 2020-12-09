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
import pl.handsome.club.repobrowser.api.GitHubApi
import pl.handsome.club.repobrowser.api.toDomain
import pl.handsome.club.repobrowser.data.someApiRepositoryDetails
import pl.handsome.club.repobrowser.data.someSearchRepository
import pl.handsome.club.repobrowser.domain.details.GetRepositoryDetailsState
import pl.handsome.club.repobrowser.repository.GithubRepository
import pl.handsome.club.repobrowser.rule.CoroutineTestRule


@ExperimentalCoroutinesApi
@FlowPreview
class RepositoryDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: RepositoryDetailsViewModel

    @Mock
    private lateinit var gitHubApi: GitHubApi

    @Mock
    private lateinit var observer: Observer<GetRepositoryDetailsState>


    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        val githubRepository =
            GithubRepository(gitHubApi, coroutineTestRule.testCoroutineDispatchers)
        viewModel = RepositoryDetailsViewModel(githubRepository)
        viewModel.getRepositoryDetailsState.observeForever(observer)
    }

    @After
    fun cleanup() {
        viewModel.getRepositoryDetailsState.removeObserver(observer)
    }

    @Test
    fun `get repository details - success`() = coroutineTestRule.runBlockingTest {
        `when`(gitHubApi.getRepository(someSearchRepository.id, someSearchRepository.ownerId))
            .thenReturn(someApiRepositoryDetails)

        viewModel.getDetails(someSearchRepository)

        verify(observer).onChanged(GetRepositoryDetailsState.InProgress)

        val expectedRepositoryDetails = someApiRepositoryDetails.toDomain()
        verify(observer).onChanged(GetRepositoryDetailsState.Success(expectedRepositoryDetails))
    }

    @Test
    fun `get repository details - error`() = coroutineTestRule.runBlockingTest {
        val exampleException = IllegalStateException()

        `when`(gitHubApi.getRepository(anyLong(), anyLong())).thenThrow(exampleException)

        viewModel.getDetails(someSearchRepository)

        verify(observer).onChanged(GetRepositoryDetailsState.InProgress)
        verify(observer).onChanged(GetRepositoryDetailsState.Error(exampleException))
    }


}