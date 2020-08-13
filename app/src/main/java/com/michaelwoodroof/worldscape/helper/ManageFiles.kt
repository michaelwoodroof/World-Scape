package com.michaelwoodroof.worldscape.helper

import android.content.Context
import android.util.Log
import com.michaelwoodroof.worldscape.content.WorldContent
import java.io.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ManageFiles(private val gc : Context) {

    // @TODO Implement Image Saving
    fun saveWorld(title : String, desc : String, genre : String, imgPath : String?,
                  color : String) : Boolean {

        try {
            // Checks if Worlds Folder exists
            val fo = gc.filesDir
            val f = File(fo, "worlds")
            if (!f.exists()) {
                f.mkdir()
            }

            // Tries to Create File
            return try {
                val cf = File(fo.absolutePath + "/worlds", generateUUID())
                cf.createNewFile()
                val fos = FileOutputStream(cf)
                val oos = ObjectOutputStream(fos)
                // Create WorldContent.WorldItem

                // @TODO Fix to real method
                // Save to Folder Path res/World_Images
                var ip = imgPath
                if (ip == null) {
                    ip = "NN"
                } else {
                    ip = "YY"
                }

                val wi = WorldContent.WorldItem(title, desc, genre, ip, color, generateUUID())
                oos.writeObject(wi)
                true
            } catch (e : Exception) {
                Log.e("Error", e.toString())
                false
            }

        } catch (e : Exception) {
            Log.e("Error", e.toString())
            return false
        }

    }

    fun getWorlds() : ArrayList<WorldContent.WorldItem> {
        val wl = ArrayList<WorldContent.WorldItem>()
        // Get Worlds
        val wf = File(gc.filesDir.absolutePath + "/worlds")

        wf.listFiles()?.forEach {
            val fis = FileInputStream(it)
            val ois = ObjectInputStream(fis)
            val wi = ois.readObject() as WorldContent.WorldItem
            wl.add(wi)
            Log.d("testData", wi.title)
        }

        return wl
    }

    private fun generateUUID() : String {
        val uuid = UUID.randomUUID()
        return uuid.toString()
    }

}