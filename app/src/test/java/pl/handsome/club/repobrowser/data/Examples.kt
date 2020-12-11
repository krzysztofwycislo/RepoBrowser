package pl.handsome.club.repobrowser.data

import pl.handsome.club.repobrowser.api.commit.ApiCommitDetails
import pl.handsome.club.repobrowser.api.commit.ApiCommitDetailsAuthor
import pl.handsome.club.repobrowser.api.commit.ApiCommitDetailsCommit
import pl.handsome.club.repobrowser.api.repository.details.ApiRepositoryDetails
import pl.handsome.club.repobrowser.api.repository.details.ApiRepositoryDetailsOwner
import pl.handsome.club.repobrowser.api.repository.search.ApiSearchRepository
import pl.handsome.club.repobrowser.domain.search.SearchRepository


val someApiSearchRepository = ApiSearchRepository(1, false, emptyList())

val someSearchRepository = SearchRepository(1, "title", 1, "ownerName","url", 5)

val someRepositoryOwner = ApiRepositoryDetailsOwner(1, "login", "avatar")

val someApiRepositoryDetails = ApiRepositoryDetails(1, "name", someRepositoryOwner, 5, "url")

val someApiCommitDetailsAuthor = ApiCommitDetailsAuthor("name", "email")

val someApiCommitDetail = ApiCommitDetails(
    ApiCommitDetailsCommit("url", "message", someApiCommitDetailsAuthor)
)

val someApiCommitDetails = listOf(
    someApiCommitDetail,
    someApiCommitDetail,
    someApiCommitDetail,
    someApiCommitDetail
)