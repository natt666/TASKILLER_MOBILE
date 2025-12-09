package com.example.taskiller.models

import java.io.Serializable
import java.util.UUID

data class Tarea(
    val Id: UUID? = null,
    var Titulo: String = "",
    var Descripcion: String = "",
    var Prioridad: Prioridades = Prioridades.BAJA,
    var FechaInicio: String = "",
    var FechaFinal: String = "",
    var listaUsuarios: MutableList<UUID> = mutableListOf(),
    var IdProyecto: UUID? = null,
    var Estado: Estados = Estados.Por_Comenzar,
    var IdTareaPadre: UUID? = null,
    var Subtareas: MutableList<UUID> = mutableListOf(),
    var HorasDedicadas: Float
): Serializable{
    enum class Prioridades(val value: Int) {
        BAJA(1),
        MEDIA(2),
        ALTA(3);

        companion object {
            fun fromInt(num: Int?): Prioridades {
                return when (num) {
                    1 -> BAJA
                    2 -> MEDIA
                    3 -> ALTA
                    else -> BAJA
                }
            }
        }
    }

    enum class Estados(val value: Int) {
        Por_Comenzar(1),
        En_Progreso(2),
        Entregado(3),
        Revisado(4),
        Bloqueado(5);

        companion object {
            fun fromInt(num: Int?): Estados {
                return when (num) {
                    1 -> Por_Comenzar
                    2 -> En_Progreso
                    3 -> Entregado
                    4 -> Revisado
                    5 -> Bloqueado
                    else -> Por_Comenzar
                }
            }
        }
    }
}
