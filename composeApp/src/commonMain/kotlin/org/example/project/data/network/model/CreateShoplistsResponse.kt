package org.example.project.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateShoplistsResponse(
val success:Boolean,
    val list_id:String
):Response()
