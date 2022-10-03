package br.com.tecnomotor.thanos.service

import org.ksoap2.serialization.KvmSerializable
import org.ksoap2.serialization.PropertyInfo
import org.ksoap2.serialization.SoapObject
import java.sql.Timestamp
import java.util.*

/**
 * @author rogerio.neo
 */
class Hchoice : KvmSerializable {
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
    val idiId: Int
        get() = getValueInteger(IDIID)
    val monNome: String
        get() = getValueString(MONNOME)
    val veiNome: String
        get() = getValueString(VEINOME)
    val motNome: String
        get() = getValueString(MOTNOME)
    val tpsNome: String
        get() = getValueString(TPSNOME)
    val sisNome: String
        get() = getValueString(SISNOME)
    val conId: Int
        get() = getValueInteger(CONID)
    val aplAnoInicial: Int
        get() = getValueInteger(APLANOINICIAL)
    val aplAnoFinal: Int
        get() = getValueInteger(APLANOFINAL)
    val hcDataHora: Date
        get() = if (obj!!.getPropertySafely(HCDATAHORA) != null) Timestamp.valueOf(
            obj!!.getPropertyAsString(HCDATAHORA)
        ) else Date()
    val hcStatus: Int
        get() = getValueInteger(HCSTATUS)
    val keySecurity: String
        get() = getValueString(KEYSECURITY)

    private fun setValue(field: String, value: Any) {
        if (obj != null) {
            obj!!.addProperty(field, value)
        }
    }

    fun setNumSerie(numSerie: String): Hchoice {
        setValue(NUMSERIE, numSerie)
        return this
    }

    fun setMac(mac: String): Hchoice {
        setValue(MAC, mac)
        return this
    }

    fun setVol(vol: String): Hchoice {
        setValue(VOL, vol)
        return this
    }

    fun setIdiId(idiId: Int): Hchoice {
        setValue(IDIID, idiId)
        return this
    }

    fun setMonNome(monNome: String): Hchoice {
        setValue(MONNOME, monNome)
        return this
    }

    fun setVeiNome(veiNome: String): Hchoice {
        setValue(VEINOME, veiNome)
        return this
    }

    fun setMotNome(motNome: String): Hchoice {
        setValue(MOTNOME, motNome)
        return this
    }

    fun setTpsNome(tpsNome: String): Hchoice {
        setValue(TPSNOME, tpsNome)
        return this
    }

    fun setSisNome(sisNome: String): Hchoice {
        setValue(SISNOME, sisNome)
        return this
    }

    fun setConId(conId: Int): Hchoice {
        setValue(CONID, conId)
        return this
    }

    fun setAplanoInicial(aplanoInicial: Int): Hchoice {
        setValue(APLANOINICIAL, aplanoInicial)
        return this
    }

    fun setAplanoFinal(aplanoFinal: Int): Hchoice {
        setValue(APLANOFINAL, aplanoFinal)
        return this
    }

    fun setHcDataHora(hcDataHora: Date): Hchoice {
        setValue(HCDATAHORA, hcDataHora)
        return this
    }

    fun setHcStatus(hcStatus: Int): Hchoice {
        setValue(HCSTATUS, hcStatus)
        return this
    }

    fun setKeySecurity(keySecurity: String): Hchoice {
        setValue(KEYSECURITY, keySecurity)
        return this
    }

    override fun getProperty(index: Int): Any? {
        when (index) {
            0 -> return numSerie
            1 -> return mac
            2 -> return vol
            3 -> return idiId
            4 -> return monNome
            5 -> return veiNome
            6 -> return motNome
            7 -> return tpsNome
            8 -> return sisNome
            9 -> return conId
            10 -> return aplAnoInicial
            11 -> return aplAnoFinal
            12 -> return hcDataHora
            13 -> return hcStatus
            14 -> return keySecurity
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
            3 -> setIdiId(value as Int)
            4 -> setMonNome(value as String)
            5 -> setVeiNome(value as String)
            6 -> setMotNome(value as String)
            7 -> setTpsNome(value as String)
            8 -> setSisNome(value as String)
            9 -> setConId(value as Int)
            10 -> setAplanoInicial(value as Int)
            11 -> setAplanoFinal(value as Int)
            12 -> setHcDataHora(value as Date)
            13 -> setHcStatus(value as Int)
            14 -> setKeySecurity(value as String)
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
                info.type = PropertyInfo.INTEGER_CLASS
                info.name = IDIID
            }
            4 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = MONNOME
            }
            5 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = VEINOME
            }
            6 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = MOTNOME
            }
            7 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = TPSNOME
            }
            8 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = SISNOME
            }
            9 -> {
                info.type = PropertyInfo.INTEGER_CLASS
                info.name = CONID
            }
            10 -> {
                info.type = PropertyInfo.INTEGER_CLASS
                info.name = APLANOINICIAL
            }
            11 -> {
                info.type = PropertyInfo.INTEGER_CLASS
                info.name = APLANOFINAL
            }
            12 -> {
                info.type = Date().javaClass
                info.name = HCDATAHORA
            }
            13 -> {
                info.type = PropertyInfo.INTEGER_CLASS
                info.name = HCSTATUS
            }
            14 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = KEYSECURITY
            }
        }
    }

    override fun toString(): String {
        return if (obj != null) obj.toString() else ""
    }

    companion object {
        private const val COUNTITEMS = 15
        private const val NUMSERIE = "numserie"
        private const val MAC = "mac"
        private const val VOL = "vol"
        private const val IDIID = "idiid"
        private const val MONNOME = "monnome"
        private const val VEINOME = "veinome"
        private const val MOTNOME = "motnome"
        private const val TPSNOME = "tpsnome"
        private const val SISNOME = "sisnome"
        private const val CONID = "conid"
        private const val APLANOINICIAL = "aplanoinicial"
        private const val APLANOFINAL = "aplanofinal"
        private const val HCDATAHORA = "hcdatahora"
        private const val HCSTATUS = "hcstatus"
        private const val KEYSECURITY = "keysecurity"
    }
}