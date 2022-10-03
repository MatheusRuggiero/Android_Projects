package br.com.tecnomotor.rasther

import android.util.Log
import androidx.lifecycle.MutableLiveData
import br.com.tecnomotor.device.Device
import br.com.tecnomotor.device.EnumCommandResult
import br.com.tecnomotor.serialport.SerialPortDescription
import br.com.tecnomotor.serialport.usb.toHex
import br.com.tecnomotor.textutil.TextUtil
import kotlinx.coroutines.*
import java.util.concurrent.TimeoutException


/**
 * Classe responsável por representar um dispositivo Rasther
 * e responsável pelo uso do KWPTM para realizar a comunicação
 */
@InternalCoroutinesApi
@DelicateCoroutinesApi
class Rasther(val commandsRasther: CommandsRasther) : Device(commandsRasther) {

    private var currentJob = GlobalScope.launch {  }
    private val rastherInfo = RastherInfo()
    val rastherInfoLiveData: MutableLiveData<RastherInfo> = MutableLiveData()

    fun getDescription(): SerialPortDescription {
        return commandsRasther.kwp.getDescription()
    }

    fun isConnected(): Boolean {
        return commandsRasther.isConnected()
    }

    /**
     * Rotina de inicialização do Rasther
     *
     * @return Corrotina encarregada de executar os comandos referentes à inicialização do dispositivo.
     */
    @InternalCoroutinesApi
    private fun initJob(demoMode: Boolean): Job{
        return GlobalScope.launch(context = Dispatchers.IO) {
            try {
                Log.i(this.javaClass.simpleName, "InitJob")
                if (startCommunication()) {
                    Log.i(this.javaClass.simpleName, "StartCommunication")
                    var dataVersion = getFirmwareVersion()
                    rastherInfo.versaoFirmware = dataVersion.first
                    rastherInfo.bootId = dataVersion.second
                    rastherInfo.numSerie = getSerialNumber()
                    if (security(rastherInfo.numSerie, commandsRasther.security.SECURITY_DIAG)) {
                        Log.i(this.javaClass.simpleName, "Security")
                        val dataEnabledVersion = getEnabledVersionRead()
                        rastherInfo.plataforma = dataEnabledVersion.first
                        rastherInfo.versao = dataEnabledVersion.second
                        rastherInfo.montadoras = makerRead()
                        if (demoMode) cmdDemonstrationMode(demoMode)
                        rastherInfoLiveData.postValue(rastherInfo)
                    } else rastherInfoLiveData.postValue(rastherInfo)
                }
            } catch (e: TimeoutException) {
                rastherInfoLiveData.postValue(rastherInfo)
            }
        }
    }

    fun initialization(demoMode: Boolean = false) {
        if(!currentJob.isActive)
            currentJob = initJob(demoMode)
        else
            println("evitando dupla entrada no job")
    }

    /**
     * Envia o comando para inicializar a comunicação: 0x01
     * @return Verdadeiro caso o Rasther responda corretamente, falso caso contrário
     */
    @InternalCoroutinesApi
    suspend fun startCommunication(): Boolean {
        Log.w(this.javaClass.simpleName, "StartComunication")
        commandsRasther.security.clearCrypt() // Sempre limpar a criptografia no StartCommunication
        val commandResult = commandsRasther.sendCommand(commandsRasther.CMD_START_COMMUNICATION)
        Log.w(this.javaClass.simpleName, "Result startComunication: ${commandResult.data.toHex()}")
        return (commandResult.result == EnumCommandResult.CorrectResponse)
    }

    /**
     * Envia o comando para finalizar a comunicação: 0x02
     * @return Verdadeiro caso o Rasther responda corretamente, falso caso contrário
     */
    @InternalCoroutinesApi
    suspend fun stopCommunication(): Boolean {
        val commandResult = commandsRasther.sendCommand(commandsRasther.CMD_STOP_COMMUNICATION)
        return (commandResult.result == EnumCommandResult.CorrectResponse)
    }

    /**
     * Envia o comando para obter a versão do firmware e boot id do Rasther: 0x05
     *
     * @return Pair(firmwareVersion, bootId)
     */
    @InternalCoroutinesApi
    private suspend fun getFirmwareVersion(): Pair<String, Int>  {
        val commandResult = commandsRasther.sendCommand(commandsRasther.CMD_FIRMWARE_VERSION)
        Log.i(this.javaClass.simpleName, "data = ${commandResult.data.toHex()}")
        var dataVersion = Pair("0.000", -1)
        if ((commandResult.result == EnumCommandResult.CorrectResponse) && (commandResult.data.size >= 5)) {
            val vFirmware: Float = TextUtil.toHexString(byteArrayOf(commandResult.data[0])).toFloat() +
                    java.lang.Float.valueOf(
                        TextUtil.toHexString(byteArrayOf(commandResult.data[1])).toInt(16).toFloat()
                    ) / 1000
            val firmwareVersion = String.format("%.3f", vFirmware).replace(",", ".")
            val bootId = Integer.parseInt(TextUtil.toHexString(byteArrayOf(commandResult.data[3])), 16)
            dataVersion = Pair(firmwareVersion,bootId)
            Log.w(this.javaClass.simpleName, "getVersion(${dataVersion})");
        }
        return dataVersion
    }

    /**
     * Envia o comando para obter o número de série do Rasther: 0x21
     * @return Número de série
     */
    @InternalCoroutinesApi
    private suspend fun getSerialNumber(): String {
        val commandResult = commandsRasther.sendCommand(commandsRasther.CMD_SERIAL_NUMBER)
        var serialNumber = "000000"
        if (commandResult.result == EnumCommandResult.CorrectResponse)
            serialNumber = ""
            try {
                for (i in 0 until commandResult.data.size) {
                    val hexText = TextUtil.toHexString(byteArrayOf(commandResult.data[i]))
                    serialNumber += hexText.toInt(16).toChar()
                }
            } catch (e: Exception) {
                System.out.println(e.message)
            }
        return serialNumber
    }

    /**
     * Envia o comando para obter a semente de segurança: 0x27
     * @return Semente da criptografia
     */
    @InternalCoroutinesApi
    private suspend fun getSeed(): CommandResult {
        return commandsRasther.sendCommand(commandsRasther.CMD_SECURITY + 0x01)
    }

    /**
     * Envia o comando 0x27 com a chave de criptografia mais o modo de acesso
     * @param serialNumber Número de série
     * @param accessMode Modo de acesso
     * @return True se autorizado o acesso
     */
    @InternalCoroutinesApi
    private suspend fun sendKey(seedArray: ByteArray, serialNumber: String, accessMode: Byte): Boolean {
        val key = commandsRasther.security.generateKey(seedArray, serialNumber, accessMode)
        val cmd = commandsRasther.CMD_SECURITY + key
        val commandResult = commandsRasther.sendCommand(cmd)
        if (commandResult.result == EnumCommandResult.CorrectResponse) {
            Log.w(this.javaClass.simpleName, key.toHex())
            commandsRasther.security.setCrypt(key)
        }
        return (commandResult.result == EnumCommandResult.CorrectResponse)
    }

    /**
     * Chama as funções que enviam os comandos de segurança 0x27 ao Rasther
     * @param serialNumber Número de série
     * @param accessMode Modo de acesso
     * @return True se autorizado o acesso
     */
    @InternalCoroutinesApi
    private suspend fun security(serialNumber: String, accessMode: Byte): Boolean {
        val seed = getSeed()
        return if (seed.result == EnumCommandResult.CorrectResponse)
            (sendKey(seed.data, serialNumber, accessMode))
        else
            false
    }

    /**
     * Envia o comando para obter a versão habilitada e a plataforma: 0x2A
     *
     * @return Pair(platform, enabledVersion
     */
    @InternalCoroutinesApi
    private suspend fun getEnabledVersionRead(): Pair<Int, Int> {
        val commandResult = commandsRasther.sendCommand(commandsRasther.CMD_VERSION_READ)
        var data = Pair(-1, -1)
        if (commandResult.result == EnumCommandResult.CorrectResponse) {
            var platform = commandResult.data[0].toHex()
            platform = Integer.parseInt(platform, 16).toString()
            var enabledVersion = commandResult.data[1].toHex()
            enabledVersion = Integer.parseInt(enabledVersion, 16).toString()
            data = Pair(platform.toInt(), enabledVersion.toInt())
        }
        return data
    }

    /**
     * Envia o comando para obter a lista de montadoras habilitada: 0x2B
     * @return ArrayList ordenado de inteiros contendo os IDs das montadoras habilitadas.
     */
    @InternalCoroutinesApi
    private suspend fun makerRead(): ArrayList<Int> {
        val commandResult = commandsRasther.sendCommand(commandsRasther.security.encrypt(commandsRasther.CMD_MAKER_READ))
        val makersList = commandResult.data
        //Log.d("Rasther", TextUtil.toHexString(makersList))

        val listaMontadorasDecimal = ArrayList<Int>()

        for(makerID in makersList){
            if (makerID != 0xFF.toByte()) {
                listaMontadorasDecimal.add(makerID.toInt())
            }
        }
        listaMontadorasDecimal.sort()
        return listaMontadorasDecimal
    }

    /**
     * Envia o comando para habilitar o modo de demonstração: 0x26
     * @param demoMode true se habilitado modo demonstração, false caso contrário
     * @return Resposta do Rasther para este comando.
     */
    @InternalCoroutinesApi
    private suspend fun cmdDemonstrationMode(demoMode: Boolean): Boolean {
        // TODO: conferir: parece estar habilitando quando não deveria
        val demo = if (demoMode) "1".toInt(16).toString() else "0".toInt(16).toString()
        val commandResult = commandsRasther.sendCommand(commandsRasther.CMD_DEMOSTRATION_MODE + TextUtil.fromHexString(demo))
        return (commandResult.result == EnumCommandResult.CorrectResponse)
    }

    override fun disconnect() {
        commandsRasther.disconnect()
    }

    fun setConnet(value: Boolean) {
        commandsRasther.setConnected(value)
    }
}


