package org.example.project.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class AddToShoplistResponse(
    val success:Boolean,
    val item_id:String
):Response()