package com.example.proyectobueno.models

import kotlinx.serialization.Serializable

// Importante, frame de las variables para las cards de la vista de cliente.
@Serializable
data class caseCliente(
    val id: Int,
    val fecha: String,
    val titulo: String,
    val descripcion: String,
    val id_usuario: Int,
    val confirmacion: Boolean,
    val created_at: String
)