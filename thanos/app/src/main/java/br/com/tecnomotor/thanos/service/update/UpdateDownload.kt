package br.com.tecnomotor.thanos.service.update

import br.com.tecnomotor.logger.Logger
import java.io.IOException
import java.io.OutputStream
import java.net.URL

//UpdateDownload.downloadFromUrl(getUrl("", 
//		newItems.get(i).getString(SettingsActivity.ITEM_FILE_NAME)), 
//		openFileSave(DataBaseHelper.DB_PATH, 
//				newItems.get(i).getString(
//                		SettingsActivity.ITEM_FILE_NAME)));
object UpdateDownload {
    val logger: Logger = Logger(showLog = true, appName = "Thanos")
    fun downloadFromUrl(
        fileUrl: String?,
        fileOutput: OutputStream?
    ) {
        try {
            val url = URL(fileUrl)
            val urlConnection = url.openConnection()
            urlConnection.connect()
            val `in` = urlConnection.getInputStream()
            val buffer = ByteArray(1024)
            var read: Int
            while (`in`.read(buffer).also { read = it } != -1) {
                fileOutput!!.write(buffer, 0, read)
            }
            `in`.close()
            fileOutput!!.flush()
            fileOutput.close()
            logger.d("UpdateProcess", "Download ($fileUrl")
        } catch (e: IOException) {
        }
    }
}