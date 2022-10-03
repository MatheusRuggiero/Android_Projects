package br.com.tecnomotor.thanos.service

import org.ksoap2.serialization.KvmSerializable
import org.ksoap2.serialization.PropertyInfo
import org.ksoap2.serialization.SoapObject
import java.sql.Timestamp
import java.util.*

/**
 * @author rogerio.neo
 */
class Estacao : KvmSerializable {
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

    private fun getValueInteger(field: String): Int {
        return if (obj!!.getPropertySafely(field) != null) Integer.valueOf(
            obj!!.getPropertyAsString(
                field
            )
        ) else 0
    }

    val numSerie: String
        get() = getValueString(NUMSERIE)
    val mac: String
        get() = getValueString(MAC)
    val vol: String
        get() = getValueString(VOL)
    val nome: String
        get() = getValueString(NOME)
    val so: String
        get() = getValueString(SO)
    val proc: String
        get() = getValueString(PROC)
    val mem: Int
        get() = getValueInteger(MEM)
    val dtUpdateRpc: Date
        get() = if (obj!!.getPropertySafely(DTUPDATERPC) != null) Timestamp.valueOf(
            obj!!.getPropertyAsString(DTUPDATERPC)
        ) else Date()
    val idiomaRpc: Int
        get() = getValueInteger(IDIOMARPC)
    val verFb: String
        get() = getValueString(VERFB)
    val user: String
        get() = getValueString(USER)
    val group: String
        get() = getValueString(GROUP)
    val keySecurity: String
        get() = getValueString(KEYSECURITY)

    private fun setValue(field: String, value: Any) {
        if (obj != null) {
            obj!!.addProperty(field, value)
        }
    }

    fun setNumSerie(numSerie: String): Estacao {
        setValue(NUMSERIE, numSerie)
        return this
    }

    fun setMac(mac: String): Estacao {
        setValue(MAC, mac)
        return this
    }

    fun setVol(vol: String): Estacao {
        setValue(VOL, vol)
        return this
    }

    fun setNome(nome: String): Estacao {
        setValue(NOME, nome)
        return this
    }

    fun setSo(so: String): Estacao {
        setValue(SO, so)
        return this
    }

    fun setProc(proc: String): Estacao {
        setValue(PROC, proc)
        return this
    }

    fun setMem(mem: Int): Estacao {
        setValue(MEM, mem)
        return this
    }

    fun setDtUpdateRpc(dtUpdateRpc: Date): Estacao {
        setValue(DTUPDATERPC, dtUpdateRpc)
        return this
    }

    fun setIdiomaRpc(idiomaRpc: Int): Estacao {
        setValue(IDIOMARPC, idiomaRpc)
        return this
    }

    fun setVerFb(verFb: String): Estacao {
        setValue(VERFB, verFb)
        return this
    }

    fun setUser(user: String): Estacao {
        setValue(USER, user)
        return this
    }

    fun setGroup(group: String): Estacao {
        setValue(GROUP, group)
        return this
    }

    fun setKeySecurity(keySecurity: String): Estacao {
        setValue(KEYSECURITY, keySecurity)
        return this
    }

    override fun getProperty(index: Int): Any? {
        when (index) {
            0 -> return numSerie
            1 -> return mac
            2 -> return vol
            3 -> return nome
            4 -> return so
            5 -> return proc
            6 -> return mem
            7 -> return dtUpdateRpc
            8 -> return idiomaRpc
            9 -> return verFb
            10 -> return user
            11 -> return group
            12 -> return keySecurity
        }
        return null
    }

    override fun getPropertyCount(): Int {
        return COUNTITEMS
    }

    override fun setProperty(index: Int, value: Any) {
        when (index) {
            0 -> setNumSerie(value as String)
            1 -> setMac(value as String)
            2 -> setVol(value as String)
            3 -> setNome(value as String)
            4 -> setSo(value as String)
            5 -> setProc(value as String)
            6 -> setMem(value as Int)
            7 -> setDtUpdateRpc(value as Date)
            8 -> setIdiomaRpc(value as Int)
            9 -> setVerFb(value as String)
            10 -> setUser(value as String)
            11 -> setGroup(value as String)
            12 -> setKeySecurity(value as String)
        }
    }

    override fun getPropertyInfo(index: Int, properties: Hashtable<*, *>?, info: PropertyInfo) {
        when (index) {
            0 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = NUMSERIE
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
                info.name = NOME
            }
            4 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = SO
            }
            5 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = PROC
            }
            6 -> {
                info.type = PropertyInfo.INTEGER_CLASS
                info.name = MEM
            }
            7 -> {
                info.type = Date().javaClass
                info.name = DTUPDATERPC
            }
            8 -> {
                info.type = PropertyInfo.INTEGER_CLASS
                info.name = IDIOMARPC
            }
            9 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = VERFB
            }
            10 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = USER
            }
            11 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = GROUP
            }
            12 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = KEYSECURITY
            }
        }
    }

    override fun toString(): String {
        return if (obj != null) obj.toString() else ""
    }

    companion object {
        private const val COUNTITEMS = 13
        private const val NUMSERIE = "numserie"
        private const val MAC = "mac"
        private const val VOL = "vol"
        private const val NOME = "nome"
        private const val SO = "so"
        private const val PROC = "proc"
        private const val MEM = "mem"
        private const val DTUPDATERPC = "dtupdaterpc"
        private const val IDIOMARPC = "idiomarpc"
        private const val VERFB = "verfb"
        private const val USER = "user"
        private const val GROUP = "group"
        private const val KEYSECURITY = "keysecurity"
    }
}