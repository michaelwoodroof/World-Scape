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
import com.michaelwoodroof.worldscape.content.CharacterContent
import com.michaelwoodroof.worldscape.content.WorldContent
import java.io.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ManageFiles(private val gc : Context) {

    // Used to Create the Folder Structure when a new world is made
    fun createFolderStructure(uid : String, gc : Context) : Boolean {

        val fo = gc.filesDir

        try {
            // Creates Worlds Folder if not already Existing
            val f = File(fo, "worlds")
            if (!f.exists()) {
                f.mkdir()
            }

            // Create Folder for the Unique World
            val filePath = "$fo/worlds"
            val fi = File(filePath, uid)
            if (!fi.exists()) {
                fi.mkdir()
            }

            // Create Folder for the Characters
            val filePathInner = "$fo/worlds/$uid"
            val fic = File(filePathInner, "characters")
            if (!fic.exists()) {
                fic.mkdir()
            }

            // Create Folder for the Stories
            val fis = File(filePathInner, "stories")
            if (!fis.exists()) {
                fis.mkdir()
            }

            // Create Folder for the Places
            val fip = File(filePathInner, "places")
            if (!fip.exists()) {
                fip.mkdir()
            }

            val fich = File(filePathInner, "chapters")
            if (!fich.exists()) {
                fich.mkdir()
            }

        } catch (e : Exception) {
            Log.e("error", e.toString())
            return false
        }

        return true

    }

    fun saveWorld(world : WorldContent.WorldItem) : Boolean {

        val fo = gc.filesDir
        return try {
            val uid = world.uid
            val filePath = File(fo.absolutePath + "/worlds/$uid")
            val fwd = File(filePath, "world_data")
            fwd.createNewFile()
            val fos = FileOutputStream(fwd)
            val oos = ObjectOutputStream(fos)
            // Create WorldContent.WorldItem
            oos.writeObject(world)
            fos.close()
            oos.close()
            true
        } catch (e : Exception) {
            Log.e("Error", e.toString())
            false
        }

    }

    fun saveCharacter(sc : CharacterContent.CharacterItem) : Boolean {
        val fo = gc.filesDir
        return try {
            true
        } catch (e : Exception) {
            Log.e("Error", e.toString())
            false
        }
    }

    fun getCharacters() : ArrayList<CharacterContent.CharacterItem> {
        val cl = ArrayList<CharacterContent.CharacterItem>()
        // Get Characters

        return cl
    }

    fun getWorlds() : ArrayList<WorldContent.WorldItem> {
        val wl = ArrayList<WorldContent.WorldItem>()
        // Get Worlds
        val wf = File(gc.filesDir.absolutePath + "/worlds")

        wf.listFiles()?.forEach {
            val fis = FileInputStream(it.absolutePath + "/world_data")
            val ois = ObjectInputStream(fis)
            val wi = ois.readObject() as WorldContent.WorldItem
            wl.add(wi)
        }

        return wl
    }

    @Suppress("DEPRECATION")
    fun saveWorldImage(uid : String, img : Uri, gcr : ContentResolver) : Boolean {
        // Check if Res Folder is Made
        // Try to Create File
        return try {
            val cf = File(gc.filesDir.absolutePath + "/worlds/$uid", "world_image")
            cf.createNewFile()
            // Create Bitmap of URI
            var bm: Bitmap?
            bm = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(gcr, Uri.parse(img.toString()))
            } else {
                val src = ImageDecoder.createSource(gcr, Uri.parse(img.toString()))
                ImageDecoder.decodeBitmap(src)
            }
            bm = bm?.let { Bitmap.createScaledBitmap(it, 300, 300, false) }
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

    }

    fun getWorldImage(uid : String) : Bitmap? {
        val bm : Bitmap? = null

        val wf = File(gc.filesDir.absolutePath + "/worlds/$uid/world_image")

        return try {
            BitmapFactory.decodeFile(wf.absolutePath)
        } catch (e : Exception) {
            Log.e("Error", e.toString())
            bm
        }

    }

    fun deleteWorld(fileName : String) : Boolean {

        return try {
            // Delete World and all Data
            val f = File(gc.filesDir.absolutePath + "/worlds/" + fileName)

            if (f.exists()) {
                f.deleteRecursively()
            }
            true
        } catch (e : Exception) {
            Log.e("Error", e.toString())
            false
        }

    }

   fun generateUUID() : String {
        val uuid = UUID.randomUUID()
        return uuid.toString()
   }

}