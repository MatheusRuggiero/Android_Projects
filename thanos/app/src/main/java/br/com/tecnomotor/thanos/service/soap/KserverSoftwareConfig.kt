package br.com.tecnomotor.thanos.service.soap

import br.com.tecnomotor.thanos.service.SoftwareConfig
import br.com.tecnomotor.thanos.service.soap.base.Kserver
import br.com.tecnomotor.thanos.service.soap.base.KserverProperty
import br.com.tecnomotor.thanos.service.soap.base.KserverResponse
//import com.eos.rastherandroid.service.SoftwareConfig
import org.ksoap2.serialization.SoapObject
import java.util.*

/**
 * @author rogerio.neo
 */
class KserverSoftwareConfig : Observable(), Observer {
    private var softwareConfig: SoftwareConfig? = null
    fun getSoftConfig(softId: Int?, nome: String?) {
        val server = Kserver(NAME_SAPCE, GetSoftConfigMethod, URL)
        server.addObserver(this)
        server.addProperty(KserverProperty("sofid", softId))
        server.addProperty(KserverProperty("nome", nome))
        server.execute()
    }

    fun getSoftConfigByNumSerie(softId: Int?, nome: String?, numSerie: String?) {
        val server = Kserver(NAME_SAPCE, GetSoftConfigByNumSerieMethod, URL)
        server.addObserver(this)
        server.addProperty(KserverProperty("sofid", softId))
        server.addProperty(KserverProperty("nome", nome))
        server.addProperty(KserverProperty("numserie", numSerie))
        server.execute()
    }

    fun getSoftwareConfig(): SoftwareConfig? {
        return softwareConfig
    }

    override fun update(o: Observable, arg: Any) {
        if (arg != null) {
            val response: KserverResponse = arg as KserverResponse
            softwareConfig = SoftwareConfig(response.obj as SoapObject)
            notifyObservers(softwareConfig)
        }
    }

    companion object {
        private const val NAME_SAPCE = "urn:rastherserver"
        private const val GetSoftConfigMethod = "GetSoftConfig"
        private const val GetSoftConfigByNumSerieMethod = "GetSoftConfigByNumSerie"
        private const val URL =
            "https://atualize.tecnomotor.com.br/knowledge/kserver_software_config.php"
        const val SOFTID = 1
    }
}