package br.com.tecnomotor.thanos.service.soap.base

import android.os.AsyncTask
import br.com.tecnomotor.logger.Logger
import org.ksoap2.SoapEnvelope
import org.ksoap2.SoapFault
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpResponseException
import org.ksoap2.transport.HttpTransportSE
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.util.*

/**
 * Classe para utilização de WebService
 * @author rogerio.neo
 */
class Kserver(
    nameSpace: String,
    methodName: String,
    url: String
) : Observable() {

    val logger: Logger = Logger(showLog = true, appName = "Thanos")

    var soapAction = "" //Nome do WebService
        protected set
    var methodName = "" //Nome do método a ser envocado
        protected set
    var nameSpace = "" //Nome da ação
        protected set
    var url = "" //URL do método
        protected set
    private val request: SoapObject
    private val serverResponse: KserverResponse
    fun addProperty(property: KserverProperty?) {
        request.addProperty(property)
    }

    fun execute() {
        val task: AsyncCall = AsyncCall()
        task.execute()
    }

    private inner class AsyncCall : AsyncTask<Void?, Void?, Void?>() {
        protected override fun doInBackground(vararg params: Void?): Void? {
            // serializar a requisição
            val soapEnvelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
            soapEnvelope.dotNet = true
            soapEnvelope.setOutputSoapObject(request)

            // cria um transporte de http
            val transport = HttpTransportSE(url)
            // transport.setReadTimeout(0) // TODO 05/08/2021 comentado pra compilar
            var tentativa = 0
            do {
                try {
                    tentativa++
                    // chama o transporte passando a ação e os dados serializados
                    transport.call(soapAction, soapEnvelope)

                    // a partir daqui é o processamento da resposta
                    val obj: Any = soapEnvelope.getResponse()
                    serverResponse.obj = obj
                    serverResponse.obj = obj
                    serverResponse.response = true
                } catch (e: HttpResponseException) {
                    e.printStackTrace()
                } catch (soapFault: SoapFault) {
                    soapFault.printStackTrace()
                } catch (e: XmlPullParserException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } while (!serverResponse.response && tentativa <= 5)
            return null
        }

        protected override fun onPostExecute(result: Void?) {
            logger.d(TAG, "response: " + serverResponse.response)
            logger.d(TAG, "Method: " + serverResponse.methodName)
            if (serverResponse.response) {
                try {
                    logger.d(
                        TAG, "value: " + "(" +
                                (serverResponse.obj?.javaClass?.simpleName) + "): " +
                                serverResponse.obj.toString()
                    )
                } catch (e: Exception) {
//                    e.printStackTrace();
                }
                notifyObservers(serverResponse)
            }
        }
    }

    companion object {
        private const val TAG = "Kserver"
    }

    /**
     * Construtor da classe para utilização de um WebService
     * @param nameSpace Nome do WebService
     * @param methodName Nome do método a ser envocado
     * @param url URL do método
     */
    init {
        setChanged() //Habilita notificação via observers
        this.nameSpace = nameSpace
        this.methodName = methodName
        soapAction = "$nameSpace#$methodName"
        this.url = url

        // Cria um objeto SoapObject (da classe ksoap) para fazer a requisição
        // passando como parâmetro o NAMESPACE e o nome do MÉTODO
        request = SoapObject(this.nameSpace, this.methodName)
        serverResponse = KserverResponse(this.methodName)
    }
}