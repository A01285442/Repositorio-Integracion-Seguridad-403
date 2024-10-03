package com.example.legalmatch.data.api.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Caso(
    val c_investigacion: String,
    val c_judicial: String,
    val caso_cerrado: Boolean,
    val created_at: LocalDateTime,
    val delito: String,
    val descripcion: String,
    val direccion_ui: String,
    val drive_link: String,
    val fiscalia_virtual: String,
    val id: Int,
    val id_abogado: Int,
    val id_cliente: Int,
    val nuc: String,
    val password_fv: String,
    val titulo: String,
    val unidad_investigacion: String
)