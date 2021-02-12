package com.paxet.evoapp.lesson10.data.network.tmdbapi

import com.paxet.evoapp.lesson10.data.db.Actors
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieCreditsAPI(

	@SerialName("cast")
	val cast: List<CastItem?> = listOf(),

	@SerialName("id")
	val id: Int? = null,

	@SerialName("crew")
	val crew: List<CrewItem?>? = null
)

@Serializable
data class CrewItem(

	@SerialName("gender")
	val gender: Int? = null,

	@SerialName("credit_id")
	val creditId: String? = null,

	@SerialName("known_for_department")
	val knownForDepartment: String? = null,

	@SerialName("original_name")
	val originalName: String? = null,

	@SerialName("popularity")
	val popularity: Double? = null,

	@SerialName("name")
	val name: String? = null,

	@SerialName("profile_path")
	val profilePath: String? = null,

	@SerialName("id")
	val id: Int? = null,

	@SerialName("adult")
	val adult: Boolean? = null,

	@SerialName("department")
	val department: String? = null,

	@SerialName("job")
	val job: String? = null
)

@Serializable
data class CastItem(

	@SerialName("cast_id")
	val castId: Int? = null,

	@SerialName("character")
	val character: String? = null,

	@SerialName("gender")
	val gender: Int? = null,

	@SerialName("credit_id")
	val creditId: String? = null,

	@SerialName("known_for_department")
	val knownForDepartment: String? = null,

	@SerialName("original_name")
	val originalName: String? = null,

	@SerialName("popularity")
	val popularity: Double? = null,

	@SerialName("name")
	val name: String? = null,

	@SerialName("profile_path")
	val profilePath: String? = null,

	@SerialName("id")
	val id: Int? = null,

	@SerialName("adult")
	val adult: Boolean? = null,

	@SerialName("order")
	val order: Int? = null
)

fun CastItem.toActors(movieId: String) : Actors {
	return Actors(id, name, movieId, profilePath)
}