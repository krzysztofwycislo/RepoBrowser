package pl.handsome.club.repobrowser.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_search_for_repository.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import pl.handsome.club.repobrowser.R
import pl.handsome.club.repobrowser.domain.search.SearchRepositoriesState
import pl.handsome.club.repobrowser.domain.search.SearchRepository
import pl.handsome.club.repobrowser.ui.adapter.RepositoryRecyclerListAdapter
import pl.handsome.club.repobrowser.util.DebounceTextWatcher
import pl.handsome.club.repobrowser.util.logError
import pl.handsome.club.repobrowser.util.safeNavigate
import pl.handsome.club.repobrowser.viewmodel.RepositoryDetailsViewModel
import pl.handsome.club.repobrowser.viewmodel.SearchRepositoryViewModel
import pl.handsome.club.repobrowser.viewmodel.ViewModelFactory


@ExperimentalCoroutinesApi
class SearchForRepositoryFragment : Fragment(R.layout.fragment_search_for_repository) {

    private val searchRepositoryViewModel: SearchRepositoryViewModel by viewModels { ViewModelFactory }
    private val repositoryDetailsViewModel: RepositoryDetailsViewModel by activityViewModels { ViewModelFactory }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchRepositoryViewModel.searchGitRepositoriesState
            .observe(viewLifecycleOwner, ::onSearchStateChanged)

        initializeSearchView()
        initializeRepositoryList()
    }

    private fun initializeRepositoryList() {
        val repositoryListAdapter = RepositoryRecyclerListAdapter(::onRepositorySelected)
        repositoryRecyclerList.adapter = repositoryListAdapter
    }

    private fun initializeSearchView() {
        val debounceTextWatcher = DebounceTextWatcher(
            lifecycleScope,
            searchRepositoryViewModel::search
        )
        repositorySearchEditText.addTextChangedListener(debounceTextWatcher)
    }

    private fun onSearchStateChanged(state: SearchRepositoriesState?) {
        if (state !is SearchRepositoriesState.InProgress) {
            progressBar.hide()
        }

        when (state) {
            is SearchRepositoriesState.InProgress -> progressBar.show()
            is SearchRepositoriesState.Success -> onSearchSuccess(state.searchRepositories)
            is SearchRepositoriesState.Error -> showError(state.throwable)
        }
    }

    private fun onSearchSuccess(searchRepositories: List<SearchRepository>) {
        (repositoryRecyclerList.adapter as RepositoryRecyclerListAdapter)
            .submitList(searchRepositories)
    }

    private fun onRepositorySelected(searchRepository: SearchRepository) {
        repositoryDetailsViewModel.getDetails(searchRepository)
        findNavController().safeNavigate(R.id.to_repositoryDetailsFragment)
    }

    // we could push it to e.g. ErrorViewModel
    // then handle it in more generic way by Activity with some exception - message mapping
    // but i will leave it this way for simplicity
    private fun showError(throwable: Throwable) {
        logError(throwable)
        Toast.makeText(requireContext(), R.string.error_something_went_wrong, Toast.LENGTH_SHORT).show()
    }

}