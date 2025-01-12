package org.example.project.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class RemoveShoplistResponse(
    val success:Boolean,
    val new_value:Boolean
):Response()