package br.com.tecnomotor.thanos.service

import org.ksoap2.serialization.KvmSerializable
import org.ksoap2.serialization.PropertyInfo
import org.ksoap2.serialization.SoapObject
import java.util.*

/**
 * @author rogerio.neo
 */
class SendValue : KvmSerializable {
    private var obj: SoapObject?

    constructor() {
        obj = SoapObject()
    }

    constructor(obj: SoapObject?) {
        this.obj = obj
    }

    private fun getValueString(field: String): String {
        return if (obj!!.getPropertySafely(field) != null) obj!!.getPropertyAsString(field) else ""
    }

    var pcName: String
        get() = getValueString(PCNAME)
        set(pcName) {
            setValue(PCNAME, pcName)
        }
    var mac: String
        get() = getValueString(MAC)
        set(mac) {
            setValue(MAC, mac)
        }
    var vol: String
        get() = getValueString(VOL)
        set(vol) {
            setValue(VOL, vol)
        }
    var motherBoard: String
        get() = getValueString(MOTHERBOARD)
        set(motherBoard) {
            setValue(MOTHERBOARD, motherBoard)
        }
    var address: String
        get() = getValueString(ADDRESS)
        set(address) {
            setValue(ADDRESS, address)
        }
    var subject: String
        get() = getValueString(SUBJECT)
        set(subject) {
            setValue(SUBJECT, subject)
        }
    var body: String
        get() = getValueString(BODY)
        set(body) {
            setValue(BODY, body)
        }
    var keySecurity: String
        get() = getValueString(KEYSECURITY)
        set(keySecurity) {
            setValue(KEYSECURITY, keySecurity)
        }

    private fun setValue(field: String, value: Any) {
        if (obj != null) {
            obj!!.addProperty(field, value)
        }
    }

    override fun getProperty(index: Int): Any? {
        when (index) {
            0 -> return pcName
            1 -> return mac
            2 -> return vol
            3 -> return motherBoard
            4 -> return address
            5 -> return subject
            6 -> return body
            7 -> return keySecurity
        }
        return null
    }

    override fun getPropertyCount(): Int {
        return COUNTITEMS
    }

    override fun setProperty(index: Int, value: Any) {
        when (index) {
            0 -> pcName = value as String
            1 -> mac = value as String
            2 -> vol = value as String
            3 -> motherBoard = value as String
            4 -> address = value as String
            5 -> subject = value as String
            6 -> body = value as String
            7 -> keySecurity = value as String
        }
    }

    override fun getPropertyInfo(index: Int, properties: Hashtable<*, *>?, info: PropertyInfo) {
        when (index) {
            0 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = PCNAME
            }
            1 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = MAC
            }
            2 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = VOL
            }
            3 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = MOTHERBOARD
            }
            4 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = ADDRESS
            }
            5 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = SUBJECT
            }
            6 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = BODY
            }
            7 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = KEYSECURITY
            }
        }
    }

    override fun toString(): String {
        return if (obj != null) obj.toString() else ""
    }

    companion object {
        private const val COUNTITEMS = 8
        private const val PCNAME = "pcname"
        private const val MAC = "mac"
        private const val VOL = "vol"
        private const val MOTHERBOARD = "motherboard"
        private const val ADDRESS = "address"
        private const val SUBJECT = "subject"
        private const val BODY = "body"
        private const val KEYSECURITY = "keysecurity"
    }
}