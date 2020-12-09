package pl.handsome.club.repobrowser.domain.details


sealed class GetCommitsDetailsState {

    object InProgress : GetCommitsDetailsState()
    data class Success(val commitsDetails: List<CommitDetails>) : GetCommitsDetailsState()
    data class Error(val throwable: Throwable) : GetCommitsDetailsState()
}
