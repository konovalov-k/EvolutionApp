package com.paxet.evoapp.lesson8.data.network.tmdbapi

import com.paxet.evoapp.lesson8.data.db.Genres
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresAPI(
	@SerialName("genres")
	val genres: List<GenresItem?>? = null
)

@Serializable
data class GenresItem(

	@SerialName("name")
	val name: String? = null,

	@SerialName("id")
	val id: Int? = null
)

fun GenresItem.toGenres() : Genres {
return Genres(
	this.id,
	this.name
)
}
