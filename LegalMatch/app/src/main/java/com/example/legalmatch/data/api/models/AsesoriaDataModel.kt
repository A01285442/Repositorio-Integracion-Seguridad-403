package com.example.legalmatch.data.api.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Asesoria(
    val c_investigacion: String,
    val c_judicial: String,
    val estado: String,
    val cliente_confirmado: Boolean,
    val cliente_denuncio: Boolean,
    val created_at: LocalDateTime?,
    val delito: String,
    val descripcion: String,
    val fecha_asesoria: LocalDateTime? = LocalDateTime(1,1,1,1,1,1),
    val id: Int,
    val id_cliente: Int,
    val nuc: String,
    val titulo: String,
    val descripcion_modificada: String
)

@Serializable
data class SendAsesoria(
    val c_investigacion: String,
    val c_judicial: String,
    val estado: String,
    val cliente_confirmado: Boolean,
    val cliente_denuncio: Boolean,
    val delito: String,
    val descripcion: String,
    val fecha_asesoria: LocalDateTime? = LocalDateTime(1,1,1,1,1,1),
    val id_cliente: Int,
    val nuc: String,
    val titulo: String,
    val descripcion_modificada: String
)