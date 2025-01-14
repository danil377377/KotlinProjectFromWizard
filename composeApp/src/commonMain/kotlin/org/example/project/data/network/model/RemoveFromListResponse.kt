package org.example.project.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class RemoveFromListResponse(
    val success:Boolean,
):Response()