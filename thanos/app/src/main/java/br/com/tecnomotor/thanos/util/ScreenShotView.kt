import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
class ScreenShotView(private var context: Context) {



    private fun getScreenShot(v: View): Bitmap? {
        // create a bitmap object
        var screenshot: Bitmap? = null
        try {
            // inflate screenshot object with Bitmap.createBitmap it requires three parameters
            // width and height of the view and the background color
            screenshot =
                Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
            // Now draw this bitmap on a canvas
            val canvas = Canvas(screenshot)
            v.draw(canvas)
        } catch (e: Exception) {
            Log.e("GFG", "Failed to capture screenshot because:" + e.message)
        }
        // return the bitmap
        return screenshot
    }

    private fun saveMediaToStorage(bitmap: Bitmap, appPath: String): Boolean {
        // Generating a file name
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        val currentDate = sdf.format(Date())
        val countImage = 0 // TODO: obter do sharedPreferences e incrementar cada vez que utilizar
        val filename = "${currentDate}_${countImage}.jpg" // Ex.: 20210723_114407_5.jpg

        // Output stream
        var fos: OutputStream? = null

        // For devices running android >= Q
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // getting the contentResolver
            context.contentResolver?.also { resolver ->

                // Content resolver will process the contentvalues
                val contentValues = ContentValues().apply {

                    // putting file information in content values
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(
                        MediaStore.MediaColumns.RELATIVE_PATH,
                        Environment.DIRECTORY_PICTURES + appPath
                    )
                }

                // Inserting the contentValues to contentResolver and getting the Uri
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                // Opening an outputstream with the Uri that we got
                fos = imageUri.let { resolver.openOutputStream(it!!) }
            }
        } else {
            // These for devices running on android < Q
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + appPath)
            if (imagesDir != null) {
                if (!imagesDir.exists()) imagesDir.mkdirs()
            } else {
                return false // ou lançar execeção quando diretório não está disponível
            }
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            // Finally writing the bitmap to the output stream that we opened
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            return true // sucesso
        }
        return false
    }

    // Pega print já formatado em ByteStream
    fun getToByteStream(v: View): ByteArray? {
        val stream = ByteArrayOutputStream()
        getScreenShot(v)?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val imagemBytes = stream.toByteArray()
        return imagemBytes
    }

    // Converte byteStream em imagem Bitmap
    fun convertToBitmap(img: ByteArray): Bitmap? {
        val imagemStream = ByteArrayInputStream(img)
        return BitmapFactory.decodeStream(imagemStream)
    }

    // Salva o print na Galery do dispositivo
    fun savePrintToGalery(v: View) {
        val bitmap = getScreenShot(v)

        // if bitmap is not null then
        // save it to gallery
        val appPath = "/Thanos"
        if (bitmap != null) {
            if (saveMediaToStorage(bitmap, appPath)) {
                Toast.makeText(
                    context,
                    "A capturada da tela foi salvo na galeria",
                    Toast.LENGTH_LONG,
                ).show()
            } else {
                Toast.makeText(
                    context,
                    "Erro ao salvar a captura na galeria",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}