package pl.handsome.club.repobrowser.api.details


data class ApiRepositoryDetails (
	val id : Long,
	val name : String,
	val owner : ApiRepositoryDetailsOwner,
	val stargazers_count : Int
)