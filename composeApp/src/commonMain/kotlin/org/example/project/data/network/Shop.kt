package org.example.project.data.network

import kotlinx.serialization.Serializable

@Serializable
data class Shop(
    val created: String,
    val id: Int,
    val name: String
)