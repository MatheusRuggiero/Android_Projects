package br.com.tecnomotor.thanos.service

import org.ksoap2.serialization.KvmSerializable
import org.ksoap2.serialization.PropertyInfo
import org.ksoap2.serialization.SoapObject
import java.util.*

/**
 * @author rogerio.neo
 */
class Update : KvmSerializable {
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

    val id: Int
        get() = getValueInteger(ID)
    val nome: String
        get() = getValueString(NOME)
    val nameUpdate: String
        get() = getValueString(NAMEUPDATE)
    val xmlParameter: String
        get() = getValueString(XMLPARAMETER)
    val pathUpdate: String
        get() = getValueString(PATHUPDATE)
    val pathUpdateTest: String
        get() = getValueString(PATHUPDATETEST)

    private fun setValue(field: String, value: Any) {
        if (obj != null) {
            obj!!.addProperty(field, value)
        }
    }

    fun setId(id: Int): Update {
        setValue(ID, id)
        return this
    }

    fun setNome(nome: String): Update {
        setValue(NOME, nome)
        return this
    }

    fun setNameUpdate(nameUpdate: String): Update {
        setValue(NAMEUPDATE, nameUpdate)
        return this
    }

    fun setXmlParameter(xmlParameter: String): Update {
        setValue(XMLPARAMETER, xmlParameter)
        return this
    }

    fun setPathUpdate(pathUpdate: String): Update {
        setValue(PATHUPDATE, pathUpdate)
        return this
    }

    fun setPathUpdateTest(pathUpdateTest: String): Update {
        setValue(PATHUPDATETEST, pathUpdateTest)
        return this
    }

    override fun getProperty(index: Int): Any? {
        when (index) {
            0 -> return id
            1 -> return nome
            2 -> return nameUpdate
            3 -> return xmlParameter
            4 -> return pathUpdate
            5 -> return pathUpdateTest
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
            2 -> setNameUpdate(value as String)
            3 -> setXmlParameter(value as String)
            4 -> setPathUpdate(value as String)
            5 -> setPathUpdateTest(value as String)
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
            2 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = NAMEUPDATE
            }
            3 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = XMLPARAMETER
            }
            4 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = PATHUPDATE
            }
            5 -> {
                info.type = PropertyInfo.STRING_CLASS
                info.name = PATHUPDATETEST
            }
        }
    }

    override fun toString(): String {
        return if (obj != null) obj.toString() else ""
    }

    companion object {
        private const val COUNTITEMS = 6
        private const val ID = "id"
        private const val NOME = "nome"
        private const val NAMEUPDATE = "nameupdate"
        private const val XMLPARAMETER = "xmlparameter"
        private const val PATHUPDATE = "pathupdate"
        private const val PATHUPDATETEST = "pathupdatetest"
    }
}