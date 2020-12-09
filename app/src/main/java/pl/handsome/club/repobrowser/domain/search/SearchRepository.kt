package pl.handsome.club.repobrowser.domain.search

data class SearchRepository(
    val id: Long,
    val title: String,
    val ownerId: Long,
    val ownerAvatarUrl: String,
    val starsCount: Int
)
