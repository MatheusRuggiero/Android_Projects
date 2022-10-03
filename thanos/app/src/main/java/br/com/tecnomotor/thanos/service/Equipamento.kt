package br.com.tecnomotor.thanos.service

import br.com.tecnomotor.logger.Logger
import org.ksoap2.serialization.KvmSerializable
import org.ksoap2.serialization.PropertyInfo
import org.ksoap2.serialization.SoapObject
import java.util.*

/**
 * @author rogerio.neo
 */
class Equipamento : KvmSerializable {
    private var obj: SoapObject?
    val logger: Logger = Logger(showLog = true, appName = "Thanos")

    constructor() {
        obj = SoapObject()
    }

    constructor(obj: SoapObject?) {
        this.obj = obj
    }

    private fun getTag(): String {
        return "Equipamento"
    }

    private fun getValueString(field: String): String {
        return try {
            if (obj?.getPropertySafely(field) != null) obj!!.getPropertyAsString(field) else ""
        } catch (e: Exception) {
            ""
        }
    }

    private fun getValueInteger(field: String): Int {
        return try {
            if (obj?.getPropertySafely(field)  != null) Integer.valueOf(obj!!.getPropertyAsString(field)) else 0
        } catch (e: Exception) {
            0
        }
    }

    val numSerie: String
        get() = getValueString(NUMSERIE)
    val nome: String
        get() = getValueString(NOME)
    val firmware: String
        get() = getValueString(FIRMWARE)
    val versao: Int
        get() = getValueInteger(VERSAO)
    val montadoras: String
        get() = getValueString(MONTADORAS)
    val tecnomotor: Int
        get() = getValueInteger(TECNOMOTOR)
    val acessoDica: Int
        get() = getValueInteger(ACESSODICA)
    val editDica: Int
        get() = getValueInteger(EDITDICA)
    val notDica: Int
        get() = getValueInteger(NOTDICA)
    val keySecurity: String
        get() = getValueString(KEYSECURITY)

    private fun setValue(field: String, value: Any) {
        if (obj != null) {
            obj!!.addProperty(field, value)
        }
    }

    fun setNumSerie(numSerie: String): Equipamento {
        setValue(NUMSERIE, numSerie)
        return this
    }

    fun setNome(nome: String): Equipamento {
        setValue(NOME, nome)
        return this
    }

    fun setFirmware(firmware: String): Equipamento {
        setValue(FIRMWARE, firmware)
        return this
    }

    fun setVersao(versao: Int): Equipamento {
        setValue(VERSAO, versao)
        return this
    }

    fun setMontadoras(montadoras: String): Equipamento {
        setValue(MONTADORAS, montadoras)
        return this
    }

    fun setTecnomotor(tecnomotor: Int): Equipamento {
        setValue(TECNOMOTOR, tecnomotor)
        return this
    }

    fun setAcessoDica(acessoDica: Int): Equipamento {
        setValue(ACESSODICA, acessoDica)
        return this
    }

    fun setEditDica(editDica: Int): Equipamento {
        setValue(EDITDICA, editDica)
        return this
    }

    fun setNotDica(notDica: Int): Equipamento {
        setValue(NOTDICA, notDica)
        return this
    }

    fun setKeySecurity(keySecurity: String): Equipamento {
        setValue(KEYSECURITY, keySecurity)
        return this
    }

    override fun getProperty(index: Int): Any? {
        when (index) {
            0 -> return numSerie
            1 -> return nome
            2 -> return firmware
            3 -> return versao
            4 -> return montadoras
            5 -> return tecnomotor
            6 -> return acessoDica
            7 -> return editDica
            8 -> return notDica
            9 -> return keySecurity
        }
        return null
    }

    override fun getPropertyCount(): Int {
        return propertyCount
    }

    override fun setProperty(index: Int, value: Any) {
        when (index) {
            0 -> setNumSerie(value as String)
            1 -> setNome(value as String)
            2 -> setFirmware(value as String)
            3 -> setVersao(value as Int)
            4 -> setMontadoras(value as String)
            5 -> setTecnomotor(value as Int)
            6 -> setAcessoDica(value as Int)
            7 -> setEditDica(value as Int)
            8 -> setNotDica(value as Int)
            9 -> setKeySecurity(value as String)
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
                info.name = NOME
            }
            2 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = FIRMWARE
            }
            3 -> {
                info.type = PropertyInfo.INTEGER_CLASS
                info.name = VERSAO
            }
            4 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = MONTADORAS
            }
            5 -> {
                info.type = PropertyInfo.INTEGER_CLASS
                info.name = TECNOMOTOR
            }
            6 -> {
                info.type = PropertyInfo.INTEGER_CLASS
                info.name = ACESSODICA
            }
            7 -> {
                info.type = PropertyInfo.INTEGER_CLASS
                info.name = EDITDICA
            }
            8 -> {
                info.type = PropertyInfo.INTEGER_CLASS
                info.name = NOTDICA
            }
            9 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = KEYSECURITY
            }
        }
    }

    override fun toString(): String {
        return if (obj != null) obj.toString() else ""
    }

    fun imprime() {
        val TAG = "Equipamento"
        logger.d(TAG, "NumSerie   :$numSerie")
        logger.d(TAG, "Nome       :$nome")
        logger.d(TAG, "Firmware   :$firmware")
        logger.d(TAG, "Versao     :$versao")
        logger.d(TAG, "Montadoras :$montadoras")
        logger.d(TAG, "Tecnomotor :$tecnomotor")
        logger.d(TAG, "AcessoDica :$acessoDica")
        logger.d(TAG, "EditDica   :$editDica")
        logger.d(TAG, "NotDica    :$notDica")
        logger.d(TAG, "KeySecurity:$keySecurity")
    }

    companion object {
//        private val field: Int
        val propertyCount = 10
//            get() = Companion.field
        private const val NUMSERIE = "numserie"
        private const val NOME = "nome"
        private const val FIRMWARE = "firmware"
        private const val VERSAO = "versao"
        private const val MONTADORAS = "montadoras"
        private const val TECNOMOTOR = "tecnomotor"
        private const val ACESSODICA = "acessodica"
        private const val EDITDICA = "editdica"
        private const val NOTDICA = "notdica"
        private const val KEYSECURITY = "keysecurity"
    }
}