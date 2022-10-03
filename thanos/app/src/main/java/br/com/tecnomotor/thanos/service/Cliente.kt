package br.com.tecnomotor.thanos.service

import br.com.tecnomotor.logger.Logger
import org.ksoap2.serialization.KvmSerializable
import org.ksoap2.serialization.PropertyInfo
import org.ksoap2.serialization.SoapObject
import java.util.*

/**
 * @author rogerio.neo
 */
class Cliente : KvmSerializable {
    private var obj: SoapObject?
    val logger: Logger = Logger(showLog = true, appName = "Thanos")

    constructor(numSerie: String, nome: String, email: String, tel: String, pais: String,
                estado: String, cidade: String, valido: Int, keySecurity: String) {
        obj = SoapObject()
        obj?.addProperty(NUMSERIE, numSerie)
        obj?.addProperty(NOME, nome)
        obj?.addProperty(EMAIL, email)
        obj?.addProperty(TEL, tel)
        obj?.addProperty(PAIS, pais)
        obj?.addProperty(ESTADO, estado)
        obj?.addProperty(CIDADE, cidade)
        obj?.addProperty(VALIDO, valido)
        obj?.addProperty(KEYSECURITY, keySecurity)
    }

    constructor(obj: SoapObject?) {
        this.obj = obj
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
            if (obj?.getPropertySafely(field) != null) Integer.valueOf(obj!!.getPropertyAsString(field)) else 0
        } catch (e: Exception) {
            0
        }
    }

    val numSerie: String
        get() = getValueString(NUMSERIE)
    val nome: String
        get() = getValueString(NOME)
    val email: String
        get() = getValueString(EMAIL)
    val tel: String
        get() = getValueString(TEL)
    val pais: String
        get() = getValueString(PAIS)
    val estado: String
        get() = getValueString(ESTADO)
    val cidade: String
        get() = getValueString(CIDADE)
    val valido: Int
        get() = getValueInteger(VALIDO)
    val keySecurity: String
        get() = getValueString(KEYSECURITY)

    override fun getProperty(index: Int): Any? {
        when (index) {
            0 -> return numSerie
            1 -> return nome
            2 -> return email
            3 -> return tel
            4 -> return pais
            5 -> return estado
            6 -> return cidade
            7 -> return valido
            8 -> return keySecurity
        }
        return null
    }

    override fun getPropertyCount(): Int {
        return propertyCount
    }

    override fun setProperty(index: Int, value: Any) {
        obj?.setProperty(index,value)
    }

    override fun getPropertyInfo(index: Int, properties: Hashtable<*, *>?, info: PropertyInfo) {
        obj?.getPropertyInfo(index, properties, info)
    }

    override fun toString(): String {
        return if (obj != null) obj.toString() else ""
    }

    fun imprime() {
        val TAG = "Cliente"
        logger.d(TAG, "NumSerie  : $numSerie")
        logger.d(TAG, "Nome      : $nome")
        logger.d(TAG, "Email     : $email")
        logger.d(TAG, "Tel       : $tel")
        logger.d(TAG, "Pais      : $pais")
        logger.d(TAG, "Estado    : $estado")
        logger.d(TAG, "Cidade    : $cidade")
        logger.d(TAG, "Valido    : $valido")
        logger.d(TAG, "keySecuriy: $keySecurity")
    }

    companion object {
        val propertyCount = 9
        //            get() = Companion.field
        private const val NUMSERIE = "numserie"
        private const val NOME = "nome"
        private const val EMAIL = "email"
        private const val TEL = "tel"
        private const val PAIS = "pais"
        private const val ESTADO = "estado"
        private const val CIDADE = "cidade"
        private const val VALIDO = "valido"
        private const val KEYSECURITY = "keysecurity"
    }
}