package br.com.tecnomotor.thanos.service

import org.ksoap2.serialization.SoapObject

/**
 * @author rogerio.neo
 */
class SoftwareConfig(private val obj: SoapObject) {
    private fun getValueString(field: String): String {
        return if (obj.getPropertySafely(field) != null) obj.getPropertyAsString(field) else ""
    }

    private fun getValueInteger(field: String): Int {
        return if (obj.getPropertySafely(field) != null) Integer.valueOf(
            obj.getPropertyAsString(
                field
            )
        ) else 0
    }

    private fun getValueBoolean(field: String): Boolean {
        return if (obj.getPropertySafely(field) != null) java.lang.Boolean.valueOf(
            obj.getPropertyAsString(
                field
            )
        ) else false
    }

    val id: Int
        get() = getValueInteger(ID)
    val softID: Int
        get() = getValueInteger(SOFID)
    val numSerie: String
        get() = getValueString(NUMSERIE)
    val nome: String
        get() = getValueString(NOME)
    val descricao: String
        get() = getValueString(DESCRICAO)
    val link: String
        get() = getValueString(LINK)
    val enable: Boolean
        get() = getValueBoolean(ENABLE)
    val visible: Boolean
        get() = getValueBoolean(VISIBLE)

    companion object {
        private const val ID = "id"
        private const val SOFID = "sofid"
        private const val NUMSERIE = "numserie"
        private const val NOME = "nome"
        private const val DESCRICAO = "descricao"
        private const val LINK = "link"
        private const val ENABLE = "enable"
        private const val VISIBLE = "visible"
    }
}