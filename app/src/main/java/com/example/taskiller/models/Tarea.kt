package com.example.taskiller.models

import java.util.UUID

data class Tarea(
    var Id: UUID? = null,
    var Titulo: String = "",
    var Descripcion: String = "",
    var Prioridad: Prioridades = Prioridades.BAJA,
    var FechaInicio: String = "",
    var FechaFinal: String = "",
    var listaUsuarios: MutableList<UUID> = mutableListOf(),
    var IdProyecto: UUID? = null,
    var Estado: Estados = Estados.Por_Comenzar,
    var IdTareaPadre: UUID? = null,
    var Subtareas: MutableList<Tarea> = mutableListOf()
) {
    enum class Prioridades {
        BAJA,
        MEDIA,
        ALTA
    }

    enum class Estados {
        Por_Comenzar,
        En_Progreso,
        Entregado,
        Revisado,
        Bloqueado
    }
}
