package pl.handsome.club.repobrowser.api

import pl.handsome.club.repobrowser.domain.SearchRepository


fun ApiSearchRepository.toDomain(): SearchRepository {
    return SearchRepository(
        id,
        name
    )
}