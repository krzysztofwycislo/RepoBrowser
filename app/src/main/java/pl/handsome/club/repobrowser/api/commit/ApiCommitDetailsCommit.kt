package pl.handsome.club.repobrowser.api.commit


data class ApiCommitDetailsCommit (
    val url : String,
    val message : String,
    val author : ApiCommitDetailsAuthor
)