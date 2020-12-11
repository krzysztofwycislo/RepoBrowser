package pl.handsome.club.repobrowser.api

import pl.handsome.club.repobrowser.api.commit.ApiCommitDetails
import pl.handsome.club.repobrowser.api.repository.details.ApiRepositoryDetails
import pl.handsome.club.repobrowser.api.repository.search.ApiSearchRepository
import pl.handsome.club.repobrowser.domain.details.CommitDetails
import pl.handsome.club.repobrowser.domain.details.RepositoryDetails
import pl.handsome.club.repobrowser.domain.search.SearchRepository


fun ApiSearchRepository.toDomain(): List<SearchRepository> {
    return items.map {
        SearchRepository(
            it.id,
            it.name,
            it.owner.id,
            it.owner.login,
            it.owner.avatar_url,
            it.stargazers_count
        )
    }
}

fun ApiRepositoryDetails.toDomain(): RepositoryDetails {
    return RepositoryDetails(
        id,
        name,
        owner.id,
        owner.login,
        owner.avatar_url,
        stargazers_count,
        html_url
    )
}

fun ApiCommitDetails.toDomain(): CommitDetails {
    return CommitDetails(
        commit.author.name,
        commit.author.email,
        commit.message,
        commit.url
    )
}