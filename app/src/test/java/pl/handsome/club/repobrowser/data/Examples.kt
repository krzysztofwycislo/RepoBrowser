package pl.handsome.club.repobrowser.data

import pl.handsome.club.repobrowser.api.ApiRepositoryDetails
import pl.handsome.club.repobrowser.api.ApiSearchRepository
import pl.handsome.club.repobrowser.domain.search.SearchRepository


val someApiSearchRepositories = listOf(
    ApiSearchRepository(1, 1,"Repo1"),
    ApiSearchRepository(2, 2, "Repo2"),
    ApiSearchRepository(3, 3, "Repo3")
)

val someSearchRepository = SearchRepository(1, 1, "test")

val someApiRepositoryDetails = ApiRepositoryDetails(1)