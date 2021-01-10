package com.paxet.evoapp.lesson7.data.tmdbapi

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
