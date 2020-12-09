package pl.handsome.club.repobrowser.api.search


data class ApiSearchRepositoryOwner (
	val login : String,
	val id : Long,
	val node_id : String,
	val avatar_url : String,
	val gravatar_id : String,
	val url : String,
	val received_events_url : String,
	val type : String
)