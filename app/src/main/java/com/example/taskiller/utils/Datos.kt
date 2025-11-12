package com.example.taskiller.utils

import Proyecto
import com.example.taskiller.models.Tarea
import com.example.taskiller.models.Usuario
import com.example.taskiller.models.Rol

data class Datos(
    var ListaProyectos: MutableList<Proyecto> = mutableListOf(),
    var ListaTareas: MutableList<Tarea> = mutableListOf(),
    var ListaUsuarios: MutableList<Usuario> = mutableListOf(),
    var ListaRoles: MutableList<Rol> = mutableListOf()
)
