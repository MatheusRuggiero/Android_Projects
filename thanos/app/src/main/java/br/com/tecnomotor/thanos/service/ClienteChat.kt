package br.com.tecnomotor.thanos.service

import org.ksoap2.serialization.SoapObject

/**
 * @author rogerio.neo
 */
class ClienteChat(private val obj: SoapObject?) {
    private fun getValueString(field: String): String {
        return if (obj!!.getPropertySafely(field) != null) obj.getPropertyAsString(field) else ""
    }

    private fun getValueInteger(field: String): Int {
        return if (obj!!.getPropertySafely(field) != null) Integer.valueOf(
            obj.getPropertyAsString(
                field
            )
        ) else 0
    }

    val numSerie: String
        get() = getValueString(NUMSERIE)
    val id: Int
        get() = getValueInteger(ID)
    val nome: String
        get() = getValueString(NOME)
    val email: String
        get() = getValueString(EMAIL)
    val tel: String
        get() = getValueString(TEL)
    val pais: String
        get() = getValueString(PAIS)
    val keySecurity: String
        get() = getValueString(KEYSECURITY)

    override fun toString(): String {
        return obj?.toString() ?: ""
    }

    companion object {
        private const val NUMSERIE = "numserie"
        private const val ID = "id"
        private const val NOME = "nome"
        private const val EMAIL = "email"
        private const val TEL = "tel"
        private const val PAIS = "pais"
        private const val KEYSECURITY = "keysecurity"
    }
}