package com.example.legalmatch.data.api.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Asesoria(
    val id: Long,                      // Para int8 en PostgreSQL, usar Long en Kotlin
    val created_at: LocalDateTime,      // Para timestamptz, puedes usar LocalDateTime
    val fecha: LocalDateTime,           // Para timestamp
    val titulo: String,                 // Para text
    val descripcion: String,            // Para text
    val confirmacion: Boolean,          // Para bool
    val id_usuario: Long                // Para int8, usar Long en Kotlin
)