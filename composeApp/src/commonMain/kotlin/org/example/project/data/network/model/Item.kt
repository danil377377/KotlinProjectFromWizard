package org.example.project.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val created: String,
    val name: String,
    val is_crossed: Boolean,
    val id: Int
)