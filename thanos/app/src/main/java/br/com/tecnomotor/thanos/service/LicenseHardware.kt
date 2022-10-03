package br.com.tecnomotor.thanos.service

import org.ksoap2.serialization.KvmSerializable
import org.ksoap2.serialization.PropertyInfo
import org.ksoap2.serialization.SoapObject
import java.util.*

/**
 * @author rogerio.neo
 */
class LicenseHardware : KvmSerializable {
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

    val erro: Int
        get() = getValueInteger(ERRO)
    val versao: String
        get() = getValueString(VERSAO)
    val montadora: String
        get() = getValueString(MONTADORA)

    private fun setValue(field: String, value: Any) {
        if (obj != null) {
            obj!!.addProperty(field, value)
        }
    }

    fun setErro(erro: Int): LicenseHardware {
        setValue(ERRO, erro)
        return this
    }

    fun setVersao(versao: String): LicenseHardware {
        setValue(VERSAO, versao)
        return this
    }

    fun setMontadora(montadora: String): LicenseHardware {
        setValue(MONTADORA, montadora)
        return this
    }

    override fun getProperty(index: Int): Any? {
        when (index) {
            0 -> return erro
            1 -> return versao
            2 -> return montadora
        }
        return null
    }

    override fun getPropertyCount(): Int {
        return COUNTITEMS
    }

    override fun setProperty(index: Int, value: Any) {
        when (index) {
            0 -> setErro(value as Int)
            1 -> setVersao(value as String)
            2 -> setMontadora(value as String)
        }
    }

    override fun getPropertyInfo(index: Int, properties: Hashtable<*, *>?, info: PropertyInfo) {
        when (index) {
            0 -> {
                info.type = PropertyInfo.INTEGER_CLASS
                info.name = ERRO
            }
            1 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = VERSAO
            }
            2 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = MONTADORA
            }
        }
    }

    override fun toString(): String {
        return if (obj != null) obj.toString() else ""
    }

    companion object {
        private const val COUNTITEMS = 3
        private const val ERRO = "erro"
        private const val VERSAO = "versao"
        private const val MONTADORA = "montadora"
    }
}