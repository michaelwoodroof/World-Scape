package com.michaelwoodroof.worldscape.helper

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import com.michaelwoodroof.worldscape.content.WorldContent
import java.io.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ManageFiles(private val gc : Context) {

    fun saveWorld(title : String, desc : String, genre : String, hasImg : Boolean,
                  color : String, uid : String) : Boolean {

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
                val wi = WorldContent.WorldItem(title, desc, genre, hasImg, color, uid)
                oos.writeObject(wi)
                fos.close()
                oos.close()
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
        }

        return wl
    }

    fun saveWorldImage(uid : String, img : Uri, gcr : ContentResolver) : Boolean {
        // Check if Res Folder is Made
        try {
            // Makes Res Folder
            val fo = gc.filesDir
            val fw = File(fo, "world_images")
            if (!fw.exists()) {
                fw.mkdir()
            }

            // Try to Create File
            return try {
                val cf = File(gc.filesDir.absolutePath + "/world_images", uid)
                cf.createNewFile()
                // Create Bitmap of URI
                var bm: Bitmap? = null
                if (Build.VERSION.SDK_INT < 28) {
                    bm = MediaStore.Images.Media.getBitmap(gcr, Uri.parse(img.toString()))
                } else {
                    val src = ImageDecoder.createSource(gcr, Uri.parse(img.toString()))
                    bm = ImageDecoder.decodeBitmap(src)
                }
                // Write Bitmap to File
                val stream : OutputStream = FileOutputStream(cf)
                bm?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                stream.flush()
                stream.close()
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

    fun getWorldImage(uid : String) : Bitmap? {
        val bm : Bitmap? = null

        val wf = File(gc.filesDir.absolutePath + "/world_images")

        wf.listFiles()?.forEach {
            if (it.name.toString() == uid) {
                return BitmapFactory.decodeFile(it.absolutePath)
            }
        }

        return bm
    }

    fun deleteWorld(fileName : String) : Boolean {

        try {
            // Delete World
            val f = File(gc.filesDir.absolutePath + "/worlds/" + fileName)
            if (f.exists()) {
                f.delete()
            }
            // Delete World_Image (if exists)
            return try {
                val fwi = File(gc.filesDir.absolutePath + "/res/world_images/" + fileName)
                if (fwi.exists()) {
                    fwi.delete()
                }
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

   fun generateUUID() : String {
        val uuid = UUID.randomUUID()
        return uuid.toString()
    }

}