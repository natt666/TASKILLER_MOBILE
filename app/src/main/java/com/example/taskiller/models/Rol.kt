package com.example.taskiller.models

import java.io.Serializable
import java.util.UUID

data class Rol(
    val Id: UUID? = null,
    var Nombre: String = "",
    var Acceso: MutableList<String> = mutableListOf()
) : Serializable
