package br.com.tecnomotor.thanos.ui.testesUnitarios.downloadFiles

import android.annotation.SuppressLint
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.*
import java.util.concurrent.Executors

/**
 * @author Matheus_Ruggiero
 */
object DownloadUtil {
    fun download(
        urlFile: String?,
        BASE_URL: String,
        downloadListener: DownloadListener
    ) {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL) // Obtained through thread pool 1 Threads, specifying callback Run in child threads.
            .callbackExecutor(Executors.newSingleThreadExecutor())
            .build()
        val service = retrofit.create(DownloadService::class.java)
        val call = service.download(urlFile)



        call!!.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                // Will Response Write to slave disk, see the following analysis for details
                // Note that this method runs in a child thread
                val fileName = urlFile?.split("/")?.last()
                val  managerClass = ManagerClass()
                val path = fileName?.let { File(fileName.let { managerClass.fileMediator(it) }, it) }
                //val path = File(managerClass.fileMediator(fileName), fileName) funcao alternativa
                println("Caminho que irá ser gravado $path")

                if (path != null) {
                    writeResponseToDisk(path, response, downloadListener)
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, throwable: Throwable) {
                downloadListener.onFail(" Network Error ~ ")
            }
        })
    }

    private fun writeResponseToDisk(
        path: File,
        response: Response<ResponseBody?>,
        downloadListener: DownloadListener
    ) {
        // From response Get the input stream and the total size
        writeFileFromIS(
            path,
            response.body()!!.byteStream(),
            response.body()!!.contentLength(),
            downloadListener
        )
    }

    private  val sBufferSize = 8192

    // Write the input stream to a file
    @SuppressLint("SetTextI18n")
    private fun writeFileFromIS(
        file: File,
        `is`: InputStream,
        totalLength: Long,
        downloadListener: DownloadListener,
    ) {
        // Start downloading
        downloadListener.onStart()

        // Create a file
        if (!file.exists()) {
            if (!file.parentFile?.exists()!!) file.parentFile?.mkdir()
            try {
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
                downloadListener.onFail("createNewFile IOException")
            }
        }
        var os: OutputStream? = null
        var currentLength: Long = 0
        try {
            os = BufferedOutputStream(FileOutputStream(file))
            val data = ByteArray(sBufferSize)
            var len: Int
            while (`is`.read(data, 0, sBufferSize).also { len = it } != -1) {
                os.write(data, 0, len)
                currentLength += len.toLong()
                // Calculate the current download progress
                downloadListener.onProgress((((currentLength * 100) / totalLength) * 100).toInt())
            }
            // Download is complete and return to the saved file path
            downloadListener.onFinish(file.absolutePath)
        } catch (e: IOException) {
            e.printStackTrace()
            downloadListener.onFail("IOException: ${e.message}")
        } finally {
            try {
                `is`.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                os?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}