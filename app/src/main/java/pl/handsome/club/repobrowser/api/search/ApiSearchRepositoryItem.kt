package pl.handsome.club.repobrowser.api.search

data class ApiSearchRepositoryItem (
	val id : Long,
	val name : String,
	val owner : ApiSearchRepositoryOwner,
	val stargazers_count : Int
)