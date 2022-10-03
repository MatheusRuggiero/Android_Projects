package br.com.tecnomotor.thanos.service

import org.ksoap2.serialization.KvmSerializable
import org.ksoap2.serialization.PropertyInfo
import org.ksoap2.serialization.SoapObject
import java.util.*

/**
 * @author rogerio.neo
 */
class Plataforma : KvmSerializable {
    private var obj: SoapObject?

    constructor() {
        obj = SoapObject()
    }

    constructor(id: Int?, nome: String?) {
        obj = SoapObject()
        setId(id)
        setNome(nome)
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

    val id: Int
        get() = getValueInteger(ID)
    val nome: String
        get() = getValueString(NOME)

    private fun setValue(field: String, value: Any?) {
        if (obj != null) {
            obj!!.addProperty(field, value)
        }
    }

    fun setId(Id: Int?): Plataforma {
        setValue(ID, Id)
        return this
    }

    fun setNome(nome: String?): Plataforma {
        setValue(NOME, nome)
        return this
    }

    override fun getProperty(index: Int): Any? {
        when (index) {
            0 -> return id
            1 -> return nome
        }
        return null
    }

    override fun getPropertyCount(): Int {
        return COUNTITEMS
    }

    override fun setProperty(index: Int, value: Any) {
        when (index) {
            0 -> setId(value as Int)
            1 -> setNome(value as String)
        }
    }

    override fun getPropertyInfo(index: Int, properties: Hashtable<*, *>?, info: PropertyInfo) {
        when (index) {
            0 -> {
                info.type = PropertyInfo.INTEGER_CLASS
                info.name = ID
            }
            1 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = NOME
            }
        }
    }

    override fun toString(): String {
        return if (obj != null) obj.toString() else ""
    }

    companion object {
        private const val COUNTITEMS = 2
        private const val ID = "ID"
        private const val NOME = "NOME"
    }
}