package pl.handsome.club.repobrowser.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.fragment_commits_history.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import pl.handsome.club.repobrowser.R
import pl.handsome.club.repobrowser.domain.details.CommitDetails
import pl.handsome.club.repobrowser.domain.details.GetCommitsDetailsState
import pl.handsome.club.repobrowser.ui.adapter.CommitsHistoryRecyclerListAdapter
import pl.handsome.club.repobrowser.viewmodel.RepositoryDetailsViewModel
import pl.handsome.club.repobrowser.viewmodel.ViewModelFactory


@ExperimentalCoroutinesApi
class CommitsHistoryListFragment : Fragment(R.layout.fragment_commits_history) {

    private val repositoryDetailsViewModel: RepositoryDetailsViewModel by activityViewModels { ViewModelFactory }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repositoryDetailsViewModel.lastCommitsLoadState
            .observe(viewLifecycleOwner, ::onLastCommitsLoadStateChanged)
    }

    private fun onLastCommitsLoadStateChanged(state: GetCommitsDetailsState?) {
        when (state) {
            is GetCommitsDetailsState.InProgress -> showProgressBar()
            is GetCommitsDetailsState.Success -> initializeView(state.commitsDetails)
            is GetCommitsDetailsState.Error -> { }
        }
    }

    private fun showProgressBar() {
        commitsHistoryList.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }

    private fun initializeView(commitsDetails: List<CommitDetails>) {
        hideProgressBar()
        commitsHistoryList.alpha = 0f
        commitsHistoryList.visibility = View.VISIBLE

        val adapter = CommitsHistoryRecyclerListAdapter()
        commitsHistoryList.adapter = adapter
        adapter.submitList(commitsDetails)

        commitsHistoryList.animate().alpha(1.0f)
    }

}