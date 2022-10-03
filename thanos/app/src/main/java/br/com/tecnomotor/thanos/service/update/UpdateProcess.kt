package br.com.tecnomotor.thanos.service.update

import android.content.Context
import android.os.AsyncTask
import br.com.tecnomotor.logger.Logger
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class UpdateProcess(
    var context: Context,
    var plaID: Int,
    var verID: Int,
    var automakers: Array<String>,
    var updateFileList: ArrayList<UpdateFile>
) : AsyncTask<Context?, String?, String?>() {
    val logger: Logger = Logger(showLog = true, appName = "Thanos")
    private val UPDATE_DIR = "update"
    private val CONFIG_DAT = "config.dat"
    private val URL_BASE = "http://download.tecnomotor.com.br/?RastherPc/"
    var listModulos: Array<Int>? = null
    var listConectores: Array<Int>? = null
    var listImagesV: Array<Int>? = null
    var listImagesJ: Array<Int>? = null
    var listManualtec: Array<Int>? = null
    private var configNumber = 0
    protected override fun onPreExecute() {
//        super.onPreExecute()
//        var db: DataBaseAdapter? = DataBaseAdapter(context)
//        db.open(0)
//        listModulos = db.getListModules(plaID, verID, automakers)
//        listConectores = db.getListImagesConector(plaID, verID, automakers)
//        listImagesV = db.getListImagesConVeic(plaID, verID, automakers)
//        listImagesJ = db.getListImagesConJ(plaID, verID, automakers)
//        listManualtec = db.getListRep(plaID, verID, automakers)
//        db.close(0)
//        db = null
//        configNumber = getConfigNumber()
    }

    private fun checkDonwloadModulo(fileName: String?): Boolean {
        var nomeModulo: Int
        var nome = ""
        try {
            if (listModulos!!.size == 0) return false
            nome = fileName!!.substring(0, fileName.length - 5)
            nomeModulo = Integer.valueOf(nome)
            if (nomeModulo < 2000) return true
        } catch (e: Exception) {
            // TODO: handle exception
            nomeModulo = 0
        }
        val index = buscaIndex(listModulos, nomeModulo)
        return index > -1 && listModulos!![index] == nomeModulo
    }

    private fun checkDonwloadConector(fileName: String?): Boolean {
        var nomeConector: Int
        var nome: String
        try {
            nome = fileName!!.substring(0, 3)
            if (!nome.equals("con", ignoreCase = true) || listConectores!!.size == 0) return false
            nome = fileName.substring(3, fileName.length - 5)
            nomeConector = Integer.valueOf(nome)
        } catch (e: Exception) {
            // TODO: handle exception
            nomeConector = 0
        }
        val index = buscaIndex(listConectores, nomeConector)
        return index > -1 && listConectores!![index] == nomeConector
    }

    private fun checkDonwloadImgV(fileName: String?): Boolean {
        var nomeImg: Int
        var nome: String
        try {
            nome = fileName!!.substring(0, 3)
            if (!nome.equals("img", ignoreCase = true) || listImagesV!!.size == 0) return false
            nome = fileName.substring(3, fileName.length - 4)
            nomeImg = Integer.valueOf(nome)
        } catch (e: Exception) {
            // TODO: handle exception
            nomeImg = 0
        }
        val index = buscaIndex(listImagesV, nomeImg)
        return index > -1 && listImagesV!![index] == nomeImg
    }

    private fun checkDonwloadImgJ(fileName: String?): Boolean {
        var nomeImg: Int
        var nome: String
        try {
            nome = fileName!!.substring(0, 3)
            if (!nome.equals("vei", ignoreCase = true) || listImagesJ!!.size == 0) return false
            nome = fileName.substring(3, fileName.length - 4)
            nomeImg = Integer.valueOf(nome)
        } catch (e: Exception) {
            // TODO: handle exception
            nomeImg = 0
        }
        val index = buscaIndex(listImagesJ, nomeImg)
        return index > -1 && listImagesJ!![index] == nomeImg
    }

    private fun checkDonwloadManualtec(fileName: String?): Boolean {
        var nomeMan: Int
        var nome: String
        try {
            if (listManualtec!!.size == 0) return false
            nome = fileName!!.substring(fileName.length - 4, fileName.length)
            if (nome.equals(".dat", ignoreCase = true)) return true
            nome = fileName.substring(0, fileName.length - 5)
            nomeMan = Integer.valueOf(nome)
        } catch (e: Exception) {
            // TODO: handle exception
            nomeMan = 0
        }
        val index = buscaIndex(listManualtec, nomeMan)
        return index > -1 && listManualtec!![index] == nomeMan
    }

    private fun getConfigNumber(): Int {
//        var configNumber: String? = null
//        val dirFile = context.getDir(UPDATE_DIR, Context.MODE_PRIVATE)
//        val fileWithinMyDir = File(dirFile, CONFIG_DAT)
//        try {
//            val fileConfig: InputStream = FileInputStream(fileWithinMyDir)
//            configNumber = fileConfig.read() as Char.toString()
//        } catch (e: FileNotFoundException) {
//            // TODO Auto-generated catch block
//            e.printStackTrace()
//        } catch (e: IOException) {
//            // TODO Auto-generated catch block
//            e.printStackTrace()
//        }
//        return Integer.valueOf(configNumber)
        return 0
    }

    private fun getUrl(dirName: String?, fileName: String?): String? {
        var url: String? = "$URL_BASE$configNumber/"
        if (!dirName!!.isEmpty()) url += "$dirName/"
        url += fileName
        return url
    }

    private fun openFileSave(
        dirFileName: String?,
        Filename: String?,
        isInternal: Boolean
    ): OutputStream? {
        var out: OutputStream? = null
        try {
            out = if (isInternal) {
                if (dirFileName != null) {
                    val dirFile = context.getDir(dirFileName, Context.MODE_PRIVATE)
                    FileOutputStream(File(dirFile, Filename))
                } else {
                    context.openFileOutput(Filename, Context.MODE_PRIVATE)
                }
            } else {
                FileOutputStream(dirFileName + Filename)
            }
        } catch (e: IOException) {
            logger.d("UpdateProcess", "openFileSave - Error: $e")
        }
        return out
    }

    protected override fun doInBackground(vararg params: Context?): String? {
        var Md5ofCurrentFile: String
        var execDownload: Boolean
        for (updateFile in updateFileList) {
            Md5ofCurrentFile = ""
            execDownload = false
            try {
                if (updateFile.md5?.isEmpty() == true) continue
                try {
                    execDownload = when (updateFile.type) {
                        UpdateFileType.Module -> checkDonwloadModulo(updateFile.fileName)
                        UpdateFileType.IMAGES -> checkDonwloadConector(updateFile.fileName) ||
                                checkDonwloadImgV(updateFile.fileName) ||
                                checkDonwloadImgJ(updateFile.fileName)
                        UpdateFileType.Manualtec -> checkDonwloadManualtec(updateFile.fileName)
                        else -> true
                    }
                    if (execDownload) {
                        Md5ofCurrentFile = ""
// TODO                       Md5ofCurrentFile = Md5Helper.getMD5Checksum(
//                            updateFile.type != UpdateFileType.Data,
//                            context,
//                            if (updateFile.type == UpdateFileType.Data) DataBaseHelper.DB_PATH.toString() + updateFile.fileName else updateFile.fileName,
//                            if (updateFile.type == UpdateFileType.Data) null else updateFile.directory
//                        )
                        logger.d(
                            "UpdateProcess",
                            "GetMD5(" + updateFile.directory + "/" + updateFile.fileName + ") - " + if (updateFile.md5 == Md5ofCurrentFile) "true" else "false"
                        )
                    }
                } catch (e: Exception) {
                    // TODO: handle exception
                    execDownload = true
                    Md5ofCurrentFile = ""
                    logger.d(
                        "UpdateProcess",
                        "GetMD5(" + updateFile.directory + "/" + updateFile.fileName + ") EXCEPTION - " +
                                (if (updateFile.md5 == Md5ofCurrentFile) "true" else "false") + " - " +
                                e.message
                    )
                }
            } finally {
                if (execDownload) {
                    if (updateFile.md5 != Md5ofCurrentFile) {
//                        TODO
//                        UpdateDownload.downloadFromUrl(
//                            getUrl(updateFile.directory, updateFile.fileName),
//                            openFileSave(
//                                if (updateFile.type == UpdateFileType.Data)
//                                    DataBaseHelper.DB_PATH else updateFile.directory, updateFile.fileName,updateFile.type != UpdateFileType.Data
//                            )
//                        )
                    }
                }
            }
        }
        return null
    }

    protected override fun onProgressUpdate(vararg values: String?) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(*values)
    }

    protected override fun onPostExecute(result: String?) {
        // TODO Auto-generated method stub
        super.onPostExecute(result)
    }

    companion object {
        private fun buscaIndex(lista: Array<Int>?, chave: Int): Int {
            return buscaIndex(lista, 0, lista!!.size - 1, chave)
        }

        private fun buscaIndex(
            lista: Array<Int>?, indexMenor: Int, indexMaior: Int,
            chave: Int
        ): Int {
            val indexMedio = (indexMaior + indexMenor) / 2
            val valorDoMeio = lista!![indexMedio]
            return if (indexMenor > indexMaior) -1 else if (valorDoMeio == chave) indexMedio else if (valorDoMeio < chave) buscaIndex(
                lista,
                indexMedio + 1,
                indexMaior,
                chave
            ) else buscaIndex(lista, indexMenor, indexMedio - 1, chave)
        }
    }
}