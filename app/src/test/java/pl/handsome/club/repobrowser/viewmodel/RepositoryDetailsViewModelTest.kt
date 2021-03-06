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
import pl.handsome.club.repobrowser.api.commit.ApiCommitDetails
import pl.handsome.club.repobrowser.api.toDomain
import pl.handsome.club.repobrowser.data.someApiCommitDetails
import pl.handsome.club.repobrowser.data.someApiRepositoryDetails
import pl.handsome.club.repobrowser.data.someSearchRepository
import pl.handsome.club.repobrowser.domain.details.GetCommitsDetailsState
import pl.handsome.club.repobrowser.domain.details.GetRepositoryDetailsState
import pl.handsome.club.repobrowser.repository.GithubRepository
import pl.handsome.club.repobrowser.rule.CoroutineTestRule


@ExperimentalCoroutinesApi
@FlowPreview
class RepositoryDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private lateinit var viewModel: RepositoryDetailsViewModel

    @Mock
    private lateinit var gitHubApi: GitHubApi

    @Mock
    private lateinit var observer: Observer<GetRepositoryDetailsState>

    @Mock
    private lateinit var commitsObserver: Observer<GetCommitsDetailsState>

    companion object {
        const val REQUIRED_COMMITS_COUNT = 3
    }


    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        val githubRepository = GithubRepository(gitHubApi, coroutineRule.testCoroutineDispatchers)
        viewModel = RepositoryDetailsViewModel(githubRepository)

        viewModel.repositoryDetailsLoadState.observeForever(observer)
        viewModel.lastCommitsLoadState.observeForever(commitsObserver)
    }

    @After
    fun cleanup() {
        viewModel.repositoryDetailsLoadState.removeObserver(observer)
        viewModel.lastCommitsLoadState.removeObserver(commitsObserver)
    }

    @Test
    fun `get repository details - success`() = coroutineRule.runBlockingTest {
        `when`(gitHubApi.getRepository(anyString(), anyString()))
            .thenReturn(someApiRepositoryDetails)
        `when`(gitHubApi.getLastCommits(anyString(), anyString()))
            .thenReturn(someApiCommitDetails)

        viewModel.getDetails(someSearchRepository)

        val expectedRepositoryDetails = someApiRepositoryDetails.toDomain()
        verify(observer).onChanged(GetRepositoryDetailsState.InProgress)
        verify(observer).onChanged(GetRepositoryDetailsState.Success(expectedRepositoryDetails))

        val expectedCommitDetails = someApiCommitDetails
            .take(REQUIRED_COMMITS_COUNT)
            .map(ApiCommitDetails::toDomain)

        verify(commitsObserver).onChanged(GetCommitsDetailsState.InProgress)
        verify(commitsObserver).onChanged(GetCommitsDetailsState.Success(expectedCommitDetails))
    }

    @Test
    fun `get repository details - error on general repository details`() =
        coroutineRule.runBlockingTest {
            val exampleException = IllegalStateException()

            `when`(gitHubApi.getRepository(anyString(), anyString()))
                .thenThrow(exampleException)
            `when`(gitHubApi.getLastCommits(anyString(), anyString()))
                .thenReturn(someApiCommitDetails)

            viewModel.getDetails(someSearchRepository)

            verify(observer).onChanged(GetRepositoryDetailsState.InProgress)
            verify(observer).onChanged(GetRepositoryDetailsState.Error(exampleException))

            val expectedCommitDetails = someApiCommitDetails
                .take(REQUIRED_COMMITS_COUNT)
                .map(ApiCommitDetails::toDomain)

            verify(commitsObserver).onChanged(GetCommitsDetailsState.InProgress)
            verify(commitsObserver).onChanged(GetCommitsDetailsState.Success(expectedCommitDetails))
        }

    @Test
    fun `get repository details - error on commits details`() = coroutineRule.runBlockingTest {
        val exampleException = IllegalStateException()

        `when`(gitHubApi.getRepository(anyString(), anyString()))
            .thenReturn(someApiRepositoryDetails)
        `when`(gitHubApi.getLastCommits(anyString(), anyString()))
            .thenThrow(exampleException)

        viewModel.getDetails(someSearchRepository)

        val expectedRepositoryDetails = someApiRepositoryDetails.toDomain()
        verify(observer).onChanged(GetRepositoryDetailsState.InProgress)
        verify(observer).onChanged(GetRepositoryDetailsState.Success(expectedRepositoryDetails))

        verify(commitsObserver).onChanged(GetCommitsDetailsState.InProgress)
        verify(commitsObserver).onChanged(GetCommitsDetailsState.Error(exampleException))
    }


}