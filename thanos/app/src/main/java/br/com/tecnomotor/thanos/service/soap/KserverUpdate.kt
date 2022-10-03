package br.com.tecnomotor.thanos.service.soap

import br.com.tecnomotor.thanos.service.Update
import br.com.tecnomotor.thanos.service.soap.base.Kserver
import br.com.tecnomotor.thanos.service.soap.base.KserverProperty
import br.com.tecnomotor.thanos.service.soap.base.KserverResponse
//import com.eos.rastherandroid.service.Update
import org.ksoap2.serialization.SoapObject
import java.util.*

/**
 * @author rogerio.neo
 */
class KserverUpdate : Observable(), Observer {
    private var lastResponse: KserverResponse? = null
    private var update: Update? = null
    fun getLastResponse(): KserverResponse? {
        return lastResponse
    }

    fun GetUpdateData(softId: Int?, numSerie: String?) {
        val server = Kserver(NAME_SAPCE, GetUpdateDataMethod, URL)
        server.addObserver(this)
        server.addProperty(KserverProperty("SoftID", softId))
        server.addProperty(KserverProperty("NumSerie", numSerie))
        server.execute()
    }

    fun getUpdate(): Update? {
        return update
    }

    override fun update(o: Observable, arg: Any) {
        if (arg != null) {
            lastResponse = arg as KserverResponse
            if (lastResponse!!.obj as String === GetUpdateDataMethod) { // TODO correto as String ou as Unit?
                update = Update(lastResponse!!.obj as SoapObject)
                notifyObservers(update)
            } else notifyObservers(lastResponse)
        }
    }

    companion object {
        private const val TAG = "KserverUpdate"
        private const val NAME_SAPCE = "urn:updatetecnomotor"
        private const val GetUpdateDataMethod = "GetUpdateData"
        private const val URL = "https://atualize.tecnomotor.com.br/knowledge/kserver_update2.php"
        const val SOFTID = 1
    }

    init {
        setChanged()
    }
}