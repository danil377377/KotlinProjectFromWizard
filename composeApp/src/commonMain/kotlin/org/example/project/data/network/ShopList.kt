package org.example.project.data.network

import kotlinx.serialization.Serializable

@Serializable
data class ShopList(
    val created: String,
    val name: String,
    val id: Int,
)