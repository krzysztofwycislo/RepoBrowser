package pl.handsome.club.repobrowser.api.commit

data class ApiCommitDetails (
	val commit : ApiCommitDetailsCommit,
	val author : ApiCommitDetailsAuthor
)