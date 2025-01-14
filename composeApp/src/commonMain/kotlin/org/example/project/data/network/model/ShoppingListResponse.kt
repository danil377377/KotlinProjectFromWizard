package org.example.project.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ShoppingListResponse(
    val success: Boolean,
    val item_list: List<Item>
):Response()