package com.example.proyectobueno.models

// Importante, frame de las variables para las cards de la vista de cliente.
data class caseCliente(
    val id: Int,
    val tituloCaso: String,
    val fechaCreacion: String,
    val idCliente: Int,
    val descripcion: String
)