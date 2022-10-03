package br.com.tecnomotor.rasther

import android.util.Log
import br.com.tecnomotor.device.Commands
import br.com.tecnomotor.device.EnumCommandResult
import br.com.tecnomotor.kwptm.KWPTM
import br.com.tecnomotor.kwptm.KWPTMException
import br.com.tecnomotor.security.Security
import br.com.tecnomotor.serialport.usb.toHex
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlin.experimental.or


/**
 * Classe responsável pela organização dos comandos da comunicação do Rasther
 */
@InternalCoroutinesApi
class CommandsRasther(kwp: KWPTM) : Commands(kwp) {
    private var connected = true

    val CMD_BAUD_RATE = byteArrayOf(0x04.toByte())
    val CMD_SEND_FW_ITEM = byteArrayOf(0x08.toByte())
    val CMD_END_UPGRADE = byteArrayOf(0x09.toByte())
    val CMD_ACK = byteArrayOf(0x0F.toByte())
    val CMD_COMMAND = byteArrayOf(0x10.toByte())
    val CMD_CHECK = byteArrayOf(0x11.toByte())
    val CMD_CHECK_MENU = byteArrayOf(0x12.toByte())
    val CMD_DEBUG_ENABLE = byteArrayOf(0x1F.toByte())
    val CMD_DEBUG_LOG = byteArrayOf(0x20.toByte())
    val CMD_SERIAL_NUMBER = byteArrayOf(0x21.toByte())
    val CMD_SECURITY = byteArrayOf(0x27.toByte())
    val CMD_VERSION_READ = byteArrayOf(0x2A.toByte()) // versão da aplicação
    val CMD_VERSION_WRITE = byteArrayOf(0x07.toByte())
    val CMD_DEMOSTRATION_MODE = byteArrayOf(0x26.toByte())
    val CMD_TIME_READ = byteArrayOf(0x2C.toByte())
    val CMD_TIME_WRITE = byteArrayOf(0x25.toByte())
    val CMD_MAKER_WRITE = byteArrayOf(0x24.toByte())
    val CMD_MAKER_READ = byteArrayOf(0x2B.toByte())
    val CMD_RTC_READ = byteArrayOf(0x2D.toByte())
    val CMD_UP_DIAG_START = byteArrayOf(0x30.toByte())
    val CMD_UP_INIT_START = byteArrayOf(0x31.toByte())
    val CMD_UP_DATA = byteArrayOf(0x32.toByte())
    val CMD_UP_END = byteArrayOf(0x33.toByte())
    val CMD_UP_VERIFY = byteArrayOf(0x34.toByte())
    val CMD_READ_MEMORY_ADDRESS = byteArrayOf(0x3A.toByte())
    val CMD_ASK_MODULE_PASSWORD = byteArrayOf(0x2F.toByte())


    // script docklight fornecido pelo Valdir, utilizar com sistema 2412
    val CMD_SEND_FRAME = byteArrayOf(0x3B.toByte(), 0x11.toByte(), 0x03.toByte(), 0x22.toByte(),
        0x00.toByte(), 0x98.toByte()) //  enviado para Rasther: "86 80 A3 3B 11 03 22 00 98 B2"

    val CMD_PROTOCOL_RX = byteArrayOf(0x3B.toByte(), 0x12.toByte()) //  enviado para Rasther: "82 80 A3 3B 12 F2"
    val CMD_CFG_PINS = byteArrayOf(0x3B, 0x13, 0x06, 0x0E) // enviado para Rasther: "84 80 A3 3B 13 06 0E 09"

    val CMD_PROTOCOL_TABLE_LE = byteArrayOf(
        0x3B.toByte(), 0x14.toByte(), 0x01.toByte(), 0x01.toByte(), 0x01.toByte(), 0x06.toByte(), 0x0E.toByte(),
        0x00.toByte(), 0x00.toByte(), 0xE8.toByte(), 0x03.toByte(), 0x00.toByte(), 0x00.toByte(), 0x10.toByte(),
        0x27.toByte(), 0x00.toByte(), 0x00.toByte(), 0x40.toByte(), 0x42.toByte(), 0x0F.toByte(), 0x00.toByte(),
        0x20.toByte(), 0x4E.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(),
        0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(),
        0x00.toByte(), 0x01.toByte(), 0x00.toByte(), 0x00.toByte(), 0x01.toByte(), 0x90.toByte(), 0xD0.toByte(),
        0x03.toByte(), 0x00.toByte(), 0x01.toByte(), 0x02.toByte(), 0x04.toByte(), 0x40.toByte(), 0x42.toByte(),
        0x0F.toByte(), 0x00.toByte(), 0x0B.toByte(), 0x03.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(),
        0xFA.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x05.toByte()) // enviado para o Rasther: "BF 80 A3 3B 14 01 01 01 06 0E 00 00 E8 03 00 00 10 27 00 00 40 42 0F 00 20 4E 00 00 00 00 00 00 00 00 00 00 00 00 00 01 00 00 01 90 D0 03 00 01 02 04 40 42 0F 00 0B 03 00 00 00 FA 00 00 00 00 00 05 73"

    val security = Security()

    suspend fun sendCommand(cmd: ByteArray, timeout: Int = 10000): CommandResult{
        var count = 0
        val commandResult = CommandResult(cmd)

        while (true) {
            count ++
            if (count > 100)//tornar 1000 um membro da classe
                throw KWPTMException("KWPTM error04")

            val TX = security.encrypt(cmd)
            Log.d(this.javaClass.simpleName, "TX: ${cmd.toHex()}")
            kwp.write(TX)
            commandResult.data = security.decrypt(kwp.read(timeout))
            Log.d(this.javaClass.simpleName, "RX: ${commandResult.data.toHex()}")

            if(commandResult.data.isEmpty()){//Rx?
                if(count>10)
                    delay(30.toLong())//tornar 300 um membro da classe
                continue //Rx no
            }
            
            Log.d(this.javaClass.simpleName, "response[0]: ${commandResult.data[0].toHex()} == ${cmd[0].or(0x40.toByte()).toHex()}")

            if(commandResult.data[0] != (cmd[0].or(0x40.toByte()))){
                if(commandResult.data[0] == 0x7F.toByte()){
                    if(commandResult.data[2] == 0x78.toByte()){
                        count = 0
                        continue
                    }else
                        throw KWPTMException("KWPTM: 0x7F")//Esse é o erro que está acontecendo
                }else{
                    throw KWPTMException("KWPTM: wrong command response")
                }
            } else {
                commandResult.result = EnumCommandResult.CorrectResponse
                if (commandResult.data.size > 1) {
                    if (commandResult.data[2] == 0x20.toByte()) {
                        //negative response
                        commandResult.result = EnumCommandResult.NegativeResponse
                    }
                }
                break
            }
        }
        if (!cmd.contentEquals(CMD_ACK))
            this.sendCommand(CMD_ACK)

        if (commandResult.data.size > 1) commandResult.data = commandResult.data.copyOfRange(1, commandResult.data.size)
        return commandResult
    }

    fun setConnected(value: Boolean) {
        connected = value
    }

    fun isConnected(): Boolean {
        return connected
    }

    override fun disconnect() {
        connected = false
        super.disconnect()
    }
}