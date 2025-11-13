package com.example.taskiller.models
import java.util.UUID
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize

data class Usuario(
    val Id: @RawValue UUID? = null,
    var Nombre: String = "",
    var Apellido: String = "",
    var Mail: String = "",
    var Contrasena: String = "",
    var Rol: UUID? = null
): Parcelable
