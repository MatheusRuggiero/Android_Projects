package br.com.tecnomotor.rasther

import br.com.tecnomotor.bluetoothserial.BTDescription
import br.com.tecnomotor.kwptm.KWPTMinfo
import br.com.tecnomotor.serialport.bluetooth.BluetoothPort

data class RastherDescription(
    private var kwptMinfo: KWPTMinfo,
    var image:Int=-1
) {
    val index: Int
        get() {
            return kwptMinfo.index
        }
    val name: String
        get() {
            return kwptMinfo.name
        }
    val address: String
        get() {
            return kwptMinfo.address
        }
    val description: BluetoothPort?
        get() {
            return if (kwptMinfo.description is BluetoothPort) return kwptMinfo.description as BluetoothPort
            else null
        }

    override fun toString(): String {
        return "RastherDescription(index=$index, name='$name', address='$address', image=$image, description=$description)"
    }


}