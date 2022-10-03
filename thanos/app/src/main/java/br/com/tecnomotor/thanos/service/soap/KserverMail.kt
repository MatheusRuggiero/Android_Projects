package br.com.tecnomotor.thanos.service.soap

import br.com.tecnomotor.thanos.service.SendValue
import br.com.tecnomotor.thanos.service.soap.base.Kserver
import br.com.tecnomotor.thanos.service.soap.base.KserverProperty
import br.com.tecnomotor.thanos.service.soap.base.KserverResponse
import java.util.*

/**
 * @author rogerio.neo
 */
class KserverMail : Observable(), Observer {
    fun getSendMail(value: SendValue?) {
        val server = Kserver(NAME_SAPCE, SendMailMethod, URL)
        server.addObserver(this)
        server.addProperty(KserverProperty("sendvalue", value))
        server.execute()
    }

    fun getSendMailReplyFrom(emailfrom: String?, value: SendValue?) {
        val server = Kserver(NAME_SAPCE, SendMailReplyFromMethod, URL)
        server.addObserver(this)
        server.addProperty(KserverProperty("emailfrom", emailfrom))
        server.addProperty(KserverProperty("sendvalue", value))
        server.execute()
    }

    override fun update(o: Observable, arg: Any) {
        if (arg != null) {
            val response: KserverResponse = arg as KserverResponse
            notifyObservers(response)
        }
    }

    companion object {
        private const val NAME_SAPCE = "urn:servermail"
        private const val SendMailMethod = "SendMail"
        private const val SendMailReplyFromMethod = "SendMailReplyFrom"
        private const val URL = "http://atualize.tecnomotor.com.br/knowledge/kserver_mail.php"
    }

    init {
        setChanged() //Habilita notificação via observers
    }
}