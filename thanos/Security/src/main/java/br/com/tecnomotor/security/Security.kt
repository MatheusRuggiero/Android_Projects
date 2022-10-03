package br.com.tecnomotor.security

import br.com.tecnomotor.textutil.TextUtil
import java.util.*
import kotlin.experimental.xor
import kotlin.jvm.Throws


class Security {

    // variáveis de criptografia
    var dataA: Byte = 0x0.toByte()
    var dataB: Byte = 0x0.toByte()

    // criptografia da senha do módulo
    var random1: Byte = 0x0.toByte()
    var random2: Byte = 0x0.toByte()

    // modos de acesso ao Rasther
    val SECURITY_SEED = 0x01.toByte()
    val SECURITY_UPGRADE = 0x03.toByte()
    val SECURITY_DIAG = 0x21.toByte()
    val SECURITY_LICENSE = 0x31.toByte()
    val SECURITY_RTC = 0xD1.toByte()
    val SECURITY_SERIAL = 0xE1.toByte()
    val SECURITY_PROD = 0xF1.toByte()

    /**
     * Limpa variáveis de criptografia
     */
    fun clearCrypt() {
        dataA = 0
        dataB = 0
    }

    /**
     * Seta variáveis de criptografia
     * @param keyMessage Chave
     */
    fun setCrypt(keyMessage: ByteArray) {
        val n1 = keyMessage[1]
        val n2 = keyMessage[3]
        val n3 = keyMessage[2]
        val n4 = keyMessage[4]
        dataA = ((n1 + n2 and 0xFF) - 12 and 0xFF).toByte()
        dataB = ((n3 + n4 and 0xFF) + 58 and 0xFF).toByte()
    }

    /**
     * Decriptografa um byte
     * @param bytes Byte a ser decriptografado
     * @return Byte processado
     */
    @Throws(NumberFormatException::class)
    fun decrypt(byte: Byte): Byte {
        return ((byte - dataA) xor dataB.toInt()).toByte()
    }

    /**
     * Decriptografa um ByteArray
     * Primeiro byte não é modificado
     * @param bytes Array a ser decriptografado
     * @return Array processado
     */
    @Throws(NumberFormatException::class)
    fun decrypt(bytes: ByteArray): ByteArray {
        var bytesOut: ByteArray = byteArrayOf()
        bytesOut += bytes[0]
        for (i in 1 until bytes.size) {
            bytesOut += decrypt(bytes[i])
        }
        return bytesOut
    }

    /**
     * Criptografa um Byte
     * @param bytes Byte a ser criptografado
     * @return Byte processado
     */
    @Throws(NumberFormatException::class)
    private fun encrypt(byte: Byte): Byte {
        return ((byte xor dataB) + dataA).toByte()
    }

    /**
     * Criptografa um ByteArray
     * Primeiro byte não é modificado
     * @param bytes Array a ser criptografado
     * @return Array processado
     */
    @Throws(NumberFormatException::class)
    fun encrypt(bytes: ByteArray): ByteArray {
        var bytesOut: ByteArray = byteArrayOf()
        bytesOut += bytes[0]
        for (i in 1 until bytes.size) {
            bytesOut += encrypt(bytes[i])
        }
        return bytesOut
    }

    /**
     * Retorna uma semente para geração da chave de acordo com o parâmetro passado.
     * @param accessMode Um dos modos de acesso do Rasther
     * @return Semente
     */
    fun getKeySeedKey(accessMode: Byte): Long {
        return when (accessMode) {
            SECURITY_SEED -> 0L
            SECURITY_UPGRADE -> 0x265A3ACDL
            SECURITY_DIAG -> 0xAE0934CAL
            SECURITY_LICENSE -> 0xA2358FE2L
            SECURITY_RTC -> 0x5AEFC568L
            SECURITY_SERIAL -> 0x21065D2AL
            SECURITY_PROD -> 0x3585D6E2L
            else -> 0x0L
        }
    }

    /**
     * Gera uma chave (key) de criptografia a partir de uma semente (seed)
     *
     * @param seedArray
     * Semente (seed) usado para gerar a chave (key)
     * @return Chave (key)
     */
    fun generateKey(
        seedArray: ByteArray,
        serialNumber: String,
        accessMode: Byte
    ): ByteArray {
        var number = serialNumber.toLong()
        var keyMask = 0x80000000L
        val mask: Long = getKeySeedKey(accessMode)
        val strSeedArray = TextUtil.toHexString(seedArray).replace(" ", "")
        val seed = strSeedArray.toLong(16)
        var key = seed

        when (accessMode) {
            SECURITY_SEED -> number = 0
            SECURITY_PROD -> number = 0xAAAAAA8CL
            SECURITY_UPGRADE -> keyMask = 0x8000000L
        }

        for (i in 0..22) {
            if (key and keyMask == keyMask) {
                key = key xor mask
                key = key xor number
            }
            key = key shl 1
        }
        var keyArray: ByteArray = byteArrayOf()
        keyArray += (accessMode) // 21 diagnóstico 03 upgrade
        keyArray += Integer.parseInt(
            checkLowerCase(java.lang.Long.toHexString(key shr 24 and 0xFF)),
            16
        ).toByte()
        keyArray += Integer.parseInt(
            checkLowerCase(java.lang.Long.toHexString(key shr 16 and 0xFF)),
            16
        ).toByte()
        keyArray += Integer.parseInt(
            checkLowerCase(java.lang.Long.toHexString(key shr 8 and 0xFF)),
            16
        ).toByte()
        keyArray += Integer.parseInt(checkLowerCase(java.lang.Long.toHexString(key and 0xFF)), 16).toByte()
        return keyArray
    }

    /**
     *
     */
    private fun checkLowerCase(message: String): String {
        var message = message
        message = if (message.length == 1) {
            "0" + message.toUpperCase(Locale.ROOT)
        } else {
            message.toUpperCase(Locale.ROOT)
        }
        return message
    }

    /**
     * Desembaralha senha do módulo utilizando duas chaves
     * @param inputArray Senha
     * @return Senha
     */
    fun unscramblePass(inputArray: ByteArray): ByteArray {

        var byteArray = byteArrayOf()
        inputArray.forEach {
            byteArray += byteArrayOf(((it xor random1) + random2).toByte())
        }
        return byteArray
    }

    /**
     * Retorna senha para um módulo específico alterando 8 dígitos correspondentes
     * @param module Número do módulo
     * @param pass String base para compor a senha.
     * Esta string é enviada pelo hardware com o novo firmware 2.114
     */
    fun composePassModule(module: Int, pass: String): String {
        var senha = ""
        try {
            val str1 = pass.substring(0, 11)
            val str2 = "0000$module"
            val str3 = pass.substring(19, pass.length)
            senha = str1 + str2 + str3
        } catch (e: Exception) {
        } // se der erro, retorna string vazia
        return senha
        // pass = "M0DuL3 F0R 00003169 7eCn0m70r 3l37r0n1cA d0 BrA51L"; // senha nova
    }
}