package br.com.tecnomotor.rasther.utils

import android.content.Context
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.zip.DataFormatException
import java.util.zip.Inflater


object ZlibUtils {

    /**
     * Descompactação de um ByteArray
     * @param bytesToDecompress Bytes a serem descompactados
     * @return ByteArray descompactado
     * @author Rogério
     */
    @Throws(DataFormatException::class)
    fun decompress(bytesToDecompress: ByteArray): ByteArray {
        val inflater = Inflater()
        val numberOfBytesToDecompress = bytesToDecompress.size
        inflater.setInput(bytesToDecompress, 0, numberOfBytesToDecompress)
        val compressionFactorMaxLikely = 100
        val bufferSizeInBytes = numberOfBytesToDecompress * compressionFactorMaxLikely
        val bytesDecompressed = ByteArray(bufferSizeInBytes)
        val returnValues: ByteArray
        try {
            val numberOfBytesAfterDecompression: Int = inflater.inflate(bytesDecompressed)
            returnValues = ByteArray(numberOfBytesAfterDecompression)
            System.arraycopy(bytesDecompressed, 0, returnValues, 0, numberOfBytesAfterDecompression)
            inflater.end()
        } catch (e: DataFormatException) {
            throw DataFormatException()
        }
        return returnValues
    }

    /**
     * Descompactação de arquivos
     * @param context Contexto da Activity
     * @param bytesToDecompress  Bytes a serem descomprimidos
     * @param fileName Nome do arquivo de saída
     * @return Próprio nome do arquivo. Em caso de erro o nome do arquivo é vazio.
     * @throws DataFormatException Senha errada
     * @throws FileNotFoundException  Arquivo não encontrado
     * @throws IOException Erro de entrada e saída
     * @author Rogério
     */
    @Throws(FileNotFoundException::class, IOException::class, DataFormatException::class)
    fun decompressToInternalFile(
        context: Context,
        bytesToDecompress: ByteArray, fileName: String
    ): String {

        try {
//        	Logger.i("ZlibUtils", "decompressToInternalFile: " + fileName);
            if (context.deleteFile(fileName)) {
//        		Logger.d("ZlibUtils", "Arquivo temporário "+fileName+" apagado");
            } else {
//        		Logger.d("ZlibUtils", "Arquivo temporário não apagado ou não existe");
            }
            val bytesDecompressed = decompress(bytesToDecompress)
            if (bytesDecompressed.isEmpty())
                return ""
            val fout: FileOutputStream = context.openFileOutput(
                fileName,
                Context.MODE_PRIVATE
            )
            fout.write(bytesDecompressed, 0, bytesDecompressed.size)
            fout.close()
            return fileName
        } catch (e: FileNotFoundException) {
            throw FileNotFoundException()
        } catch (e: IOException) {
            throw IOException()
        } catch (e: DataFormatException) {
            throw DataFormatException()
        }
        return ""
    }
}