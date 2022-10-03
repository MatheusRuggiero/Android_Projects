package br.com.tecnomotor.rasther.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.*

/**
 * Classe responsável pela manipulação de arquivos no dispositivo
 */
class FilesUtils(private val context: Context) {

    val TAG: String = "FilesUtils"

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: FilesUtils? = null

        fun getInstance(context: Context? = null):FilesUtils {
            if (instance == null)
                if (context == null)
                    throw  java.lang.Exception("O contexto deve ser enviado pelo menos na primeira vez que o método getInstance(contexto) for chamado")
                else
                    instance = FilesUtils(context.applicationContext)
            return instance!!
        }
    }


    /**
     * Copia arquivos config.xml para armazenamento interno
     * Função extendida para a classe FileUtils de android.os.FileUtils
     * @throws IOException Erro de entrada e saída
     */
    @Throws(IOException::class)
    fun copyConfigToInternal():FilesUtils {
        Log.d(TAG, "copyConfigToInternal")
        val assetManager = context.assets
        val path: String = AppPaths.DIR_TO_INTERNAL + "/" + AppPaths.DIR_CONFIG
        val sourceFileNames: Array<String>
        try {
            sourceFileNames = assetManager.list(path) as Array<String>
            for (fileName in sourceFileNames) {
                Log.d(TAG,"Config:  $fileName")
                copyFileToInternalStorage(
                        "$path/$fileName", null, fileName
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return this
    }

    /**
     * Copia arquivos Atec para armazenamento interno
     * Função extendida para a classe FileUtils de android.os.FileUtils
     * @throws IOException Erro de entrada e saída
     */
    @Throws(IOException::class)
    fun copyAtecsToInternal():FilesUtils {
        Log.d(TAG, "copyAtecsToInternal")
        val assetManager = context.assets
        val path: String = AppPaths.DIR_TO_INTERNAL + "/" + AppPaths.DIR_ATECS
        val sourceFileNames: Array<String>
        try {
            sourceFileNames = assetManager.list(path) as Array<String>
            for (fileName in sourceFileNames) {
                Log.d("ATEC: ", fileName)
                copyFileToInternalStorage(
                    "$path/$fileName", AppPaths.DIR_ATECS, fileName
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return this
    }

    fun copyImagesToInternal():FilesUtils {
        Log.d(TAG, "copyImages")
        val assetManager = context.assets
        val path: String = AppPaths.DIR_IMAGES
        val sourceFileNames: Array<String>
        try {
            sourceFileNames = assetManager.list(path) as Array<String>
            for (fileName in sourceFileNames) {
                Log.d(TAG, "Image:  $fileName")
                copyFileToInternalStorage(
                    "$path/$fileName", AppPaths.DIR_IMAGES, fileName
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return this
    }

    fun copyPositionsToInternal():FilesUtils {
        Log.d(TAG, "copyPositions")
        val assetManager = context.assets
        val path: String = AppPaths.DIR_POSITIONS
        val sourceFileNames: Array<String>
        try {
            sourceFileNames = assetManager.list(path) as Array<String>
            for (fileName in sourceFileNames) {
                Log.d(TAG, "Position:  $fileName")
                copyFileToInternalStorage(
                    "$path/$fileName", AppPaths.DIR_POSITIONS, fileName
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return this
    }

    /**
     * Copia arquivos HTMLs para armazenamento interno
     * Função extendida para a classe FileUtils de android.os.FileUtils
     * @throws IOException Erro de entrada e saída
     */
    fun copyHtmlToInternal(){
        println("$TAG, copyHTML")
        val assetManager = context.assets
        val path: String = AppPaths.DIR_HTML
        val sourceFileNames: Array<String>
        try {
            sourceFileNames = assetManager.list(path) as Array<String>
            for (fileName in sourceFileNames) {
                println(" HTML copiado para a pasta:  $fileName")
                copyFileToInternalStorage(
                    "$path/$fileName", AppPaths.DIR_HTML, fileName
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("EXception HTML", "$e")
        }
    }



    /**
     * Copia um arquivo para o armazenamento interno
     * @throws Exception Qual exceção?
     */
    @Throws(Exception::class)
    fun copyFileToInternalStorage(
        sourceFileName: String, destDirName: String?, destFileName: String
    ) {
        val assetManager = context.assets
        val inputStream: InputStream
        val outputStream: OutputStream
        try {
            inputStream = assetManager.open(sourceFileName)
            if (destDirName != null) {
                val dirFile: File = context
                    .getDir(destDirName, AppCompatActivity.MODE_PRIVATE)
                outputStream = FileOutputStream(File(dirFile, destFileName))
            } else {
                outputStream = context
                    .openFileOutput(destFileName, AppCompatActivity.MODE_PRIVATE)
            }
            val buffer = ByteArray(1024)
            var read: Int
            while (inputStream.read(buffer).also { read = it } != -1) {
                outputStream.write(buffer, 0, read)
            }
            inputStream.close()
            outputStream.flush()
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception()
        }
    }
}

