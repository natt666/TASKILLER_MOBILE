package com.example.taskiller
import Datos
import android.content.Context
import android.util.Log
import com.example.taskiller.models.Tarea
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.FileWriter
import java.io.InputStreamReader

fun Context.getDatos(): Datos? {
    return try {
        val dir = File(filesDir, "datajson")
        if (!dir.exists()) {
            dir.mkdirs()
        }

        val jsonFile = File(dir, "TaskillerData.json")

        if (!jsonFile.exists()) {
            assets.open("TaskillerData.json").use { input ->
                FileOutputStream(jsonFile).use { output ->
                    input.copyTo(output)
                }
            }
        }

        val reader = InputStreamReader(jsonFile.inputStream())

        val gson = GsonBuilder()
                .registerTypeAdapter(Tarea.Prioridades::class.java, JsonDeserializer { json, _, _ ->
                    when (json.asString) {
                        "BAJA" -> Tarea.Prioridades.BAJA
                        "MEDIA" -> Tarea.Prioridades.MEDIA
                        "ALTA" -> Tarea.Prioridades.ALTA
                        "1" -> Tarea.Prioridades.BAJA
                        "2" -> Tarea.Prioridades.MEDIA
                        "3" -> Tarea.Prioridades.ALTA
                        else -> "ERROR"
                    }
                })
                .registerTypeAdapter(Tarea.Estados::class.java, JsonDeserializer { json, _, _ ->
                    when (json.asString) {
                        "Por_Comenzar" -> Tarea.Estados.Por_Comenzar
                        "0" -> Tarea.Estados.Por_Comenzar
                        "En_Progreso" -> Tarea.Estados.En_Progreso
                        "1" -> Tarea.Estados.En_Progreso
                        "Entregado" -> Tarea.Estados.Entregado
                        "2" -> Tarea.Estados.Entregado
                        "Revisado" -> Tarea.Estados.Revisado
                        "3" -> Tarea.Estados.Revisado
                        "Bloqueado" -> Tarea.Estados.Bloqueado
                        "4" -> Tarea.Estados.Bloqueado
                        else -> "ERROR"
                    }
                })
                .create()

            gson.fromJson(reader, Datos::class.java)

    } catch (e: Exception) {
        Log.e("FileUtils", "Error leyendo JSON desde data", e)
        null
    }
}



fun Context.guardarDatos(datos: Datos) {
    try {
        val jsonDir = File(filesDir, "datajson")
        if (!jsonDir.exists()) {
            jsonDir.mkdirs()
        }
        val jsonFile = File(jsonDir, "TaskillerData.json")

        val gson = Gson()
        val jsonString = gson.toJson(datos)

        FileWriter(jsonFile).use { writer ->
            writer.write(jsonString)
        }
    } catch (e: Exception) {
        Log.e("FileUtils", "Error guardando JSON", e)
    }
}
