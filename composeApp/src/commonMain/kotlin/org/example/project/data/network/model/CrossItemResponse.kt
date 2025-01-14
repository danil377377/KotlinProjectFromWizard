package org.example.project.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class CrossItemResponse (
    val success:Boolean,
    val rows_affected: Int
):Response()