package com.example.taskiller.models
import java.util.UUID
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Tarea(
    val Id: @RawValue UUID? = null,
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
): Parcelable{
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
