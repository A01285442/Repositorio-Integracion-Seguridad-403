package com.example.legalmatch.data.api.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Asesoria(
    val c_investigacion: String,
    val c_judicial: String,
    val cancelado: Boolean,
    val cliente_confirmado: Boolean,
    val cliente_denuncio: Boolean,
    val created_at: LocalDateTime,
    val delito: String,
    val descripcion: String,
    val fecha_asesoria: LocalDateTime,
    val id: Int,
    val id_cliente: Int,
    val nuc: String,
    val titulo: String
)