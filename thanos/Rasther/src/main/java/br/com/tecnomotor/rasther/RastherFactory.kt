package br.com.tecnomotor.rasther

import android.content.Context
import android.util.Log
import br.com.tecnomotor.device.Device
import br.com.tecnomotor.device.DeviceFactory
import br.com.tecnomotor.kwptm.KWPTM
import br.com.tecnomotor.serialport.SerialPortType
import kotlinx.coroutines.*

/**
 * Define métodos para criação de dispositivos tipo Rasther
 */
@OptIn(InternalCoroutinesApi::class, DelicateCoroutinesApi::class)
class RastherFactory(private val context: Context) : DeviceFactory(context, SerialPortType.BLUETOOTH)  {

    private fun isRastherBox(name: String):Boolean {
        return (name.lowercase().contains("rasther"))
    }

    private fun isVCI(name: String): Boolean {
        return (name.lowercase().contains("tm547-vci"))
    }

    fun getDevicesList():RastherList{
        Log.w(this.javaClass.simpleName, "getDevicesList")
        val deviceList = RastherList()
        val list = this.deviceList(arrayOf("Rasther", "VCI"))//name = "Rasther")
        var img: Int = -1
        for((index, it) in list.withIndex()){
            img = if (isRastherBox(it.name)) R.drawable.img_tm536
            else if (isVCI(it.name)) R.drawable.img_vci
            else -1
            val rastherDescription =
                RastherDescription(it, img)
            Log.w(this.javaClass.simpleName, "- $rastherDescription")
            deviceList.add(rastherDescription)
        }
        return deviceList
    }

    suspend fun createRastherDevice(address: String): Rasther {
        val ret = createDevice(address) as Rasther
        return ret
    }

    private fun checkTargetSource(index: Int, kwptm: KWPTM) {
        val deviceList = getDevicesList()
        if (isRastherBox(deviceList[index].name)) {
            Log.d(this.javaClass.simpleName, "SetTargetSource: RastherBox")
            kwptm.setSource(byteArrayOf(0x80.toByte()))
                .setTarget(byteArrayOf(0xA3.toByte()))
        } else if (isVCI(deviceList[index].name))
            Log.d(this.javaClass.simpleName, "SetTargetSource: VCI")
            kwptm.setSource(byteArrayOf(0x81.toByte()))
                .setTarget(byteArrayOf(0xA3.toByte()))
    }

    /**
     * Cria dispositivo baseado no índice referente aos dispositivos disponíveis
     */
    override suspend fun createDevice(index: Int): Rasther {
        val newKWP = this.getDevice(index)
        //Define target e source no kwptm para ser útil no rasther
        checkTargetSource(index, newKWP)
        val commandsRasther = CommandsRasther(newKWP)
        return Rasther(commandsRasther)
    }

    fun getIndexByAddress(address: String):Int {
        var index = -1
        for(devDescription in getDevicesList())
            if(devDescription.address == address)
                index = devDescription.index
        return index
    }

    override suspend fun createDevice(description: String): Device {
        val newKWP = this.getDevice(description)
        //Define target e source no kwptm para ser útil no rasther
        checkTargetSource(getIndexByAddress(description), newKWP)
        val commandsRasther = CommandsRasther(newKWP)
        return Rasther(commandsRasther)
    }

    suspend fun createRasther(address: String): Rasther {
        return createDevice(address) as Rasther
    }

}
