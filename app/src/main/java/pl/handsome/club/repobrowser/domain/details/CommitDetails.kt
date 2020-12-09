package pl.handsome.club.repobrowser.domain.details

data class CommitDetails(
    val authorName: String,
    val authorEmail: String,
    val message: String,
    val htmlUrl: String
)
