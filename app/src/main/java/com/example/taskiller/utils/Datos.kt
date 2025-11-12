package com.example.taskiller.utils

import Proyecto
import com.example.taskiller.models.Tarea
import com.example.taskiller.models.Usuario
import com.example.taskiller.models.Rol

data class Datos(
    var listaProyectos: MutableList<Proyecto> = mutableListOf(),
    var listaTareas: MutableList<Tarea> = mutableListOf(),
    var listaUsuarios: MutableList<Usuario> = mutableListOf(),
    var listaRoles: MutableList<Rol> = mutableListOf()
)
