package org.example.project.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class GetAllShopListsResponse(
    val shop_list: List<ShopList>,
    val success: Boolean
): Response()