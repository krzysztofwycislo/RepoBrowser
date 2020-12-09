package pl.handsome.club.repobrowser.api

import pl.handsome.club.repobrowser.domain.details.RepositoryDetails
import pl.handsome.club.repobrowser.domain.search.SearchRepository


fun ApiSearchRepository.toDomain(): SearchRepository {
    return SearchRepository(
        id,
        ownerId,
        name
    )
}
fun ApiRepositoryDetails.toDomain(): RepositoryDetails {
    return RepositoryDetails(
        id
    )
}