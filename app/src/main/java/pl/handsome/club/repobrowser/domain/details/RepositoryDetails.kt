package pl.handsome.club.repobrowser.domain.details

data class RepositoryDetails(
    val id: Long,
    val title: String,
    val ownerId: Long,
    val ownerName: String,
    val ownerAvatarUrl: String,
    val starsCount: Int
)
