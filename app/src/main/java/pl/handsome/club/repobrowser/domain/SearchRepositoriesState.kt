package pl.handsome.club.repobrowser.domain

sealed class SearchRepositoriesState {

    object InProgress: SearchRepositoriesState()
    data class Success(val searchRepositories: List<SearchRepository>): SearchRepositoriesState()
    data class Error(val throwable: Throwable): SearchRepositoriesState()

}
