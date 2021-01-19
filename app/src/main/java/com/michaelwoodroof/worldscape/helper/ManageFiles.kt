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
import com.michaelwoodroof.worldscape.structure.MyCharacter
import com.michaelwoodroof.worldscape.structure.World
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

    fun saveWorld(world : World) : Boolean {

        val fo = gc.filesDir
        return try {
            val uid = world.uid
            val filePath = File(fo.absolutePath + "/worlds/$uid")
            val fwd = File(filePath, "world_data")
            fwd.createNewFile()
            val fos = FileOutputStream(fwd)
            val oos = ObjectOutputStream(fos)
            //  Write World to File
            oos.writeObject(world)
            fos.close()
            oos.close()
            true
        } catch (e : Exception) {
            Log.e("error", e.toString())
            false
        }

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
            Log.e("error", e.toString())
            false
        }

    }

    fun getWorlds() : ArrayList<World> {
        val wl = ArrayList<World>()
        // Get Worlds
        val wf = File(gc.filesDir.absolutePath + "/worlds")

        wf.listFiles()?.forEach {
            val fis = FileInputStream(it.absolutePath + "/world_data")
            val ois = ObjectInputStream(fis)
            val wi = ois.readObject() as World
            wl.add(wi)
        }

        return wl
    }

    fun getWorldImage(uid : String) : Bitmap? {
        val bm : Bitmap? = null

        val wf = File(gc.filesDir.absolutePath + "/worlds/$uid/world_image")

        return try {
            BitmapFactory.decodeFile(wf.absolutePath)
        } catch (e : Exception) {
            Log.e("error", e.toString())
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
            Log.e("error", e.toString())
            false
        }

    }

    fun saveCharacter(sc : MyCharacter, wuid : String) : Boolean {
        // wuid is the World's UID
        val uid = sc.uid
        val fo = gc.filesDir.absolutePath + "/worlds/$wuid/characters/$uid"

        try {
            val foc = File(fo)
            if (!foc.exists()) {
                foc.mkdir()
            }
        } catch (e : Exception) {
            return false
        }

        return try {
            val fc = File(fo, "character_data")
            fc.createNewFile()
            val fos = FileOutputStream(fc)
            val oos = ObjectOutputStream(fos)
            // Write Character to File
            oos.writeObject(sc)
            fos.close()
            oos.close()
            true
        } catch (e : Exception) {
            Log.e("error", e.toString())
            false
        }
    }

    @Suppress("DEPRECATION")
    fun saveCharacterImage(uid : String, wuid : String, img: Uri, gcr: ContentResolver) : Boolean {
        val fo = gc.filesDir.absolutePath + "/worlds/$wuid/characters/$uid"
        return try {
            val fi = File(fo, "character_image")
            fi.createNewFile()
            var bm: Bitmap?
            bm = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(gcr, Uri.parse(img.toString()))
            } else {
                val src = ImageDecoder.createSource(gcr, Uri.parse(img.toString()))
                ImageDecoder.decodeBitmap(src)
            }
            bm = bm?.let { Bitmap.createScaledBitmap(it, 300, 300, false) }
            // Write Bitmap to File
            val stream : OutputStream = FileOutputStream(fi)
            bm?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
            true
        } catch (e : Exception) {
            Log.e("error", e.toString())
            false
        }
    }

    fun getCharacters(wuid : String, limit : Int) : ArrayList<MyCharacter> {
        val cl = ArrayList<MyCharacter>()
        // Get Characters
        val cf = File(gc.filesDir.absolutePath + "/worlds/$wuid/characters")
        var count = 0

        cf.listFiles()?.forEach {
            val fis = FileInputStream(it.absolutePath + "/character_data")
            val ois = ObjectInputStream(fis)
            val ci = ois.readObject() as MyCharacter
            cl.add(ci)
            if (count == limit) {
                return cl
            }
            count++
        }

        return cl
    }

    fun getCharacterImage(wuid : String, uid : String) : Bitmap? {
        val bm : Bitmap? = null

        val cf = File(gc.filesDir.absolutePath + "/worlds/$wuid/characters/$uid/character_image")

        return try {
            BitmapFactory.decodeFile(cf.absolutePath)
        } catch (e : Exception) {
            Log.e("error", e.toString())
            bm
        }
    }

    fun deleteCharacter(fileName : String) : Boolean {
        return false //@TODO Implement
    }

   fun generateUUID() : String {
        val uuid = UUID.randomUUID()
        return uuid.toString()
   }

}