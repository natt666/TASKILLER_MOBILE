import java.io.Serializable
import java.util.UUID

data class Proyecto(
    val Id: UUID? = null,
    var Titulo: String = "",
    var Descripcion: String = "",
    var FechaInicio: String = "",
    var FechaFinal: String = "",
    var Estado: Estados = Estados.Por_Comenzar,
    var listaUsuarios: MutableList<UUID> = mutableListOf()
) : Serializable {
    enum class Estados {
        Por_Comenzar,
        En_Progreso,
        Entregado,
        Revisado,
        Bloqueado
    }
}
