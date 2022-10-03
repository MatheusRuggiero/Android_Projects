package br.com.tecnomotor.thanos.service.soap

import br.com.tecnomotor.thanos.service.ClienteChat
import br.com.tecnomotor.thanos.service.soap.base.Kserver
import br.com.tecnomotor.thanos.service.soap.base.KserverProperty
import br.com.tecnomotor.thanos.service.soap.base.KserverResponse
import org.ksoap2.serialization.SoapObject
import java.util.*

/**
 * @author rogerio.neo
 */
class KserverChat : Observable(), Observer {
    fun getClient(numSerie: String?, keySecurity: String?) {
        val server = Kserver(NAME_SAPCE, GetClienteMethod, URL)
        server.addObserver(this)
        server.addProperty(KserverProperty("numserie", numSerie))
        server.addProperty(KserverProperty("keysecurity", keySecurity))
        server.execute()
    }

    fun getSendMail(softName: String?, token: String?, subject: String?, body: String?) {
        val server = Kserver(NAME_SAPCE, SendMailMethod, URL)
        server.addObserver(this)
        server.addProperty(KserverProperty("softname", softName))
        server.addProperty(KserverProperty("token", token))
        server.addProperty(KserverProperty("subject", subject))
        server.addProperty(KserverProperty("body", body))
        server.execute()
    }

    fun getSendMailReplyFrom(
        softName: String?, token: String?, addressFrom: String?,
        subject: String?, body: String?
    ) {
        val server = Kserver(NAME_SAPCE, SendMailReplyFromMethod, URL)
        server.addObserver(this)
        server.addProperty(KserverProperty("softname", softName))
        server.addProperty(KserverProperty("token", token))
        server.addProperty(KserverProperty("addressfrom", addressFrom))
        server.addProperty(KserverProperty("subject", subject))
        server.addProperty(KserverProperty("body", body))
        server.execute()
    }

    override fun update(o: Observable, arg: Any) {
        if (arg != null) {
            val response: KserverResponse = arg as KserverResponse
            val cliente = ClienteChat(response.obj as SoapObject)
            notifyObservers(cliente)
        }
    }

    companion object {
        private const val NAME_SAPCE = "urn:chatserver"
        private const val GetClienteMethod = "GetClient"
        private const val SendMailMethod = "SendMail"
        private const val SendMailReplyFromMethod = "SendMailReplyFrom"
        private const val URL = "https://atualize.tecnomotor.com.br/knowledge/kserver_chat.php"
    }

    init {
        setChanged() //Habilita notificação via observers
    }
}