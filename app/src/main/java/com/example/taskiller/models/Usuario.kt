package com.example.taskiller.models

import java.util.UUID

data class Usuario(
    var Id: UUID? = null,
    var Nombre: String = "",
    var Apellido: String = "",
    var Mail: String = "",
    var Contrasena: String = "",
    var Rol: UUID? = null
)
