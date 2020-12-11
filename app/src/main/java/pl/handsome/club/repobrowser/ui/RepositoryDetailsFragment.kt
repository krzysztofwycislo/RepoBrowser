package pl.handsome.club.repobrowser.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_repository_details.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import pl.handsome.club.repobrowser.BuildConfig
import pl.handsome.club.repobrowser.R
import pl.handsome.club.repobrowser.domain.details.GetRepositoryDetailsState
import pl.handsome.club.repobrowser.domain.details.RepositoryDetails
import pl.handsome.club.repobrowser.util.logError
import pl.handsome.club.repobrowser.viewmodel.RepositoryDetailsViewModel
import pl.handsome.club.repobrowser.viewmodel.ViewModelFactory


@ExperimentalCoroutinesApi
class RepositoryDetailsFragment : Fragment(R.layout.fragment_repository_details) {

    private val repositoryDetailsViewModel: RepositoryDetailsViewModel by activityViewModels { ViewModelFactory }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repositoryDetailsViewModel.repositoryDetailsLoadState
            .observe(viewLifecycleOwner, ::onRepositoryDetailsLoadStateChanged)

        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun onRepositoryDetailsLoadStateChanged(state: GetRepositoryDetailsState?) {
        when (state) {
            is GetRepositoryDetailsState.InProgress -> showLoadingScreen()
            is GetRepositoryDetailsState.Success -> initializeView(state.repositoryDetails)
            is GetRepositoryDetailsState.Error -> onLoadStateError(state.throwable)
        }
    }

    private fun initializeView(repositoryDetails: RepositoryDetails) {
        hideLoadingScreen()

        repoAuthorNameText.text = repositoryDetails.ownerName
        numberOfStarsText.text = getString(
            R.string.number_of_stars_with_value,
            repositoryDetails.starsCount
        )
        authorEmailText.text = repositoryDetails.title

        Glide.with(this)
            .load(repositoryDetails.ownerAvatarUrl)
            .centerCrop()
            .into(authorAvatarImage)

        viewOnlineButton.setOnClickListener {
            val url = Uri.parse(repositoryDetails.repositoryUrl)
            val browserIntent = Intent(Intent.ACTION_VIEW, url)
            startActivity(browserIntent)
        }

        shareRepoButton.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, repositoryDetails.title)
            shareIntent.putExtra(Intent.EXTRA_TEXT, repositoryDetails.repositoryUrl)
            startActivity(Intent.createChooser(shareIntent, "Choose one"))
        }
    }

    private fun showLoadingScreen() {
        progressBar.visibility = View.VISIBLE
        repositoryDetailsContent.visibility = View.GONE
    }

    private fun hideLoadingScreen() {
        progressBar.visibility = View.GONE
        repositoryDetailsContent.alpha = 0f
        repositoryDetailsContent.visibility = View.VISIBLE
        repositoryDetailsContent.animate().alpha(1.0f)
    }

    // we could push it to e.g. ErrorViewModel
    // then handle it in more generic way by Activity with some exception - message mapping
    // but i will leave it this way for simplicity
    private fun onLoadStateError(throwable: Throwable) {
        hideLoadingScreen()
        logError(throwable)
        Toast.makeText(requireContext(), R.string.error_something_went_wrong, Toast.LENGTH_SHORT)
            .show()
        findNavController().navigateUp()
    }

}