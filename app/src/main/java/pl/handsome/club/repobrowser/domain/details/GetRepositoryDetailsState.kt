package pl.handsome.club.repobrowser.domain.details


sealed class GetRepositoryDetailsState {

    object InProgress : GetRepositoryDetailsState()
    data class Success(val repositoryDetails: RepositoryDetails) : GetRepositoryDetailsState()
    data class Error(val throwable: Throwable) : GetRepositoryDetailsState()

}
