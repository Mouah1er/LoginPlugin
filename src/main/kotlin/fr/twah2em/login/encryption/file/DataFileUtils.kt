package fr.twah2em.login.encryption.file

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import fr.twah2em.login.Main
import java.io.File
import java.io.FileWriter

/**
 * Get and set data in json file
 */
class DataFileUtils(main: Main) {

    private val file: File = main.dataFile
    private val gson = GsonBuilder()
        .setPrettyPrinting()
        .create()

    fun get(path: String): String? {
        val json = gson.fromJson(file.reader(), JsonObject::class.java) ?: return null

        return json.get(path).asString
    }

    fun set(path: String, value: String) {
        val json = gson.fromJson(file.reader(), JsonObject::class.java) ?: JsonObject()

        json.addProperty(path, value)

        FileWriter(file).use {
            it.write(gson.toJson(json))
        }
    }
}