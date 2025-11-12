import java.util.UUID

data class Proyecto(
    var Id: UUID? = null,
    var Titulo: String = "",
    var Descripcion: String = "",
    var FechaInicio: String = "",
    var FechaFinal: String = "",
    var Estado: Estados = Estados.Por_Comenzar,
    var ListaUsuarios: MutableList<UUID> = mutableListOf()
) {
    enum class Estados {
        Por_Comenzar,
        En_Progreso,
        Entregado,
        Revisado,
        Bloqueado
    }
}
