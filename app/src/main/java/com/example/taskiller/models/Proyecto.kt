import java.util.UUID
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Proyecto(
    val Id: @RawValue UUID? = null,
    var Titulo: String = "",
    var Descripcion: String = "",
    var FechaInicio: String = "",
    var FechaFinal: String = "",
    var Estado: Estados = Estados.Por_Comenzar,
    var listaUsuarios: MutableList<UUID> = mutableListOf()
): Parcelable{
    enum class Estados {
        Por_Comenzar,
        En_Progreso,
        Entregado,
        Revisado,
        Bloqueado
    }
}
