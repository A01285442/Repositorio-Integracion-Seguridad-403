package com.example.legalmatch.data.api.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Usuario(
    val id: Int? = null,
    val created_at: LocalDateTime? = null,
    val matricula: String? = null,
    val correo: String,
    val nombre: String,
    val fecha_nacimiento: LocalDateTime,
    val rol: Int,
    val contrasena: String,
)