package pl.handsome.club.repobrowser.api.search


data class ApiSearchRepository (
	val total_count : Int,
	val incomplete_results : Boolean,
	val items : List<ApiSearchRepositoryItem>
)