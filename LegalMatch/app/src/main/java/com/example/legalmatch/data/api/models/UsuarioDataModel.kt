package com.example.legalmatch.data.api.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Usuario(
    val contraseña: String,
    val correo: String,
    val created_at: LocalDateTime?,
    val fecha_nacimiento: LocalDateTime,
    val id: Int?,
    val matricula: String,
    val nombre: String,
    val rol: String,
    val sexo: String
)