package com.example.legalmatch.data.api.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class EstudianteCaso(
    val id: Int,
    val created_at: LocalDateTime,
    val estudiante_id: Int,
    val caso_id: Int
)

@Serializable
data class SendEstudianteCaso(
     val estudiante_id: Int,
     val caso_id: Int
)