package br.com.tecnomotor.rasther

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

/**
 * @Atuhor Matheus_Ruggiero
 */
@InternalCoroutinesApi
@OptIn(DelicateCoroutinesApi::class)
class RastherInsterface {

    private var currentJob = GlobalScope.launch {  }
    lateinit var rasther: Rasther



//        suspend fun startCommunication() = rasther.startCommunication()
//        suspend fun stopCommunication() = rasther.stopCommunication()
//        suspend fun getVersao() = rasther.getVersion()
//        suspend fun getMontadoras() = rasther.makerRead()
//        suspend fun getSerialNumber() = rasther.getSerialNumber()
//        fun getEcu() = rasther.getEcu(data: ByteArray)
//        fun getMeasuresNumber() = rasther.getMeasuresNumber(data: ByteArray)
//        fun getMeasureId() = rasther.getMeasureId(data: ByteArray)
//        fun getMeasureType() = rasther.getMeasureType(data: ByteArray)
//        suspend fun cmdVehicle() = rasther.cmdVehicle(command: ByteArray)
//        //fun getSeed() = rasther.getS
//        //fun sendKey() = rasther.sen
//        suspend fun enabledVersionRead() = rasther.enabledVersionRead()
//        suspend fun cmdDemonstrationMode() = rasther.cmdDemonstrationMode(demoMode: Boolean)
//        fun getModulePass() = rasther.getModulePass(_module: Int=0)
//        suspend fun getReset() = rasther.getReset()
//        suspend fun startDiagDownload() = rasther.startDiagDownload()
//        suspend fun verifyCurrentFirmware() = rasther.verifyCurrentFirmware(itens: ArrayList<String>)
//        //fun getVerifyFirmwareMessage() = rasther.get
//        //fun decrypt() = rasther.de
//        //fun createMessageFromString() = rasther.
//        suspend fun dataDiagDownload() = rasther.dataDiagDownload(s: String)
//        suspend fun endDiagDownload() = rasther.endDiagDownload()
//        suspend fun sendFrame() = rasther.sendFrame()
//        suspend fun protocolRx() = rasther.protocolRx()
//        suspend fun configurePins() = rasther.configurePins()
//        suspend fun protocolTableLE() = rasther.protocolTableLE()

}