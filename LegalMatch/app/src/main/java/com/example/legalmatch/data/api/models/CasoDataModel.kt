package com.example.legalmatch.data.api.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Caso(
    val id: Int,
    val created_at: LocalDateTime,
    val es_victima: Boolean,
    val delito: String,
    val fiscalia: String,
    val carpeta: String,
    val id_asesoria: Int,
    val estatus: Int
)