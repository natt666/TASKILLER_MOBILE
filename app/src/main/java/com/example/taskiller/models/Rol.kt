package com.example.taskiller.models

import java.util.UUID

data class Rol(
    var Id: UUID? = null,
    var Nombre: String = "",
    var Acceso: MutableList<String> = mutableListOf()
)