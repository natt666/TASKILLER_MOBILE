package com.example.taskiller.models

import java.io.Serializable
import java.util.UUID

data class Usuario(
    val Id: UUID? = null,
    var Nombre: String = "",
    var Apellido: String = "",
    var Mail: String = "",
    var Contrasena: String = "",
    var Rol: UUID? = null
) : Serializable
