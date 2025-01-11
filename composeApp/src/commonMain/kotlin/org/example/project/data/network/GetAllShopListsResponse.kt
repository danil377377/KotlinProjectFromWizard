package org.example.project.data.network

import kotlinx.serialization.Serializable

@Serializable
data class GetAllShopListsResponse(
    val shop_list: List<Shop>,
    val success: Boolean
)