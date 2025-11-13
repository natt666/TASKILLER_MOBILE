package com.example.taskiller.models
import java.util.UUID
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize

data class Rol(
    val Id: @RawValue UUID? = null,
    var Nombre: String = "",
    var Acceso: MutableList<String> = mutableListOf()
): Parcelable