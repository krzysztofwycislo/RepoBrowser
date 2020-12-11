package pl.handsome.club.repobrowser.domain.search

data class SearchRepository(
    val id: Long,
    val repoName: String,
    val ownerId: Long,
    val ownerName: String,
    val ownerAvatarUrl: String,
    val starsCount: Int
)
