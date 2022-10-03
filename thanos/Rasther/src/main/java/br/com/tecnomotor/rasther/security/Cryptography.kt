package br.com.tecnomotor.rasther.security

import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import kotlin.experimental.xor

/**
 * Relacionado à criptografia dos módulos
 */
object Cryptography {
    private val TAG = "Cryptography"

    /**
     * Descriptografa um arquivo
     * @param fileModule Arquivo contendo o módulo
     * @param key Chave de criptografia
     * @param numberString Número do módulo
     * @return ByteArray com o conteúdo
     * @throws FileNotFoundException Arquivo não encontrado
     * @throws IOException erro de entrada e saída
     */
    @Throws(FileNotFoundException::class, IOException::class)
    fun decriptFileToByte(fileModule: File,
                          key: String, number: Int): ByteArray {
        try {
            val file = FileInputStream(fileModule)
            val length: Long = fileModule.length()
            var aux: Byte = 0
            val returnByte = ByteArray(length.toInt())
            var j = 0
            for (i in 0 until length) {
                aux = file.read().toByte()
                aux = (aux - key.length).toByte()
                aux = (aux xor key[j].toByte()) as Byte
                aux = (aux xor (number / 256).toByte()) as Byte
                aux = (aux xor (number % 256).toByte()) as Byte
                if (j == key.length - 1) j = 0 else j++
                returnByte[i.toInt()] = aux
            }
            file.close()
            return returnByte
        } catch (e: FileNotFoundException) {
//            e.printStackTrace()
            throw FileNotFoundException()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return byteArrayOf()
    }
}