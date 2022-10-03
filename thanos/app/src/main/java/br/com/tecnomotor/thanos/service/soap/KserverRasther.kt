package br.com.tecnomotor.thanos.service.soap

import br.com.tecnomotor.logger.Logger
import br.com.tecnomotor.thanos.service.*
import br.com.tecnomotor.thanos.service.soap.base.Kserver
import br.com.tecnomotor.thanos.service.soap.base.KserverProperty
import br.com.tecnomotor.thanos.service.soap.base.KserverResponse
import org.ksoap2.serialization.SoapObject
import java.util.*

/**
 * @author rogerio.neo
 */
class KserverRasther : Observable, Observer {
    private var cliente: Cliente? = null
    private var equipamento: Equipamento? = null
    private var plataformaList: PlataformaList? = null
    private var licenseHardware: LicenseHardware? = null
    private var lastVersion: LastVersionResponse? = null
    private var setClientResponse: SetClientResponse? = null
    private var setEquipmentResponse: SetEquipmentResponse? = null
    private var lastResponse: KserverResponse? = null

    val logger: Logger = Logger(showLog = true, appName = "Thanos")

    constructor() {
        setChanged()
    }

    constructor(observer: Observer?) : super() {
        addObserver(observer)
    }

    fun setCliente(cliente: Cliente?) {
        val server = Kserver(NAME_SAPCE, SetClientMethod, URL)
        server.addObserver(this)
        server.addProperty(KserverProperty("client", cliente))
        server.execute()
    }

    fun getCliente(numSerie: String?, keySecurity: String?) {
        val server = Kserver(NAME_SAPCE, GetClientMethod, URL)
        server.addObserver(this)
        server.addProperty(KserverProperty("numserie", numSerie))
        server.addProperty(KserverProperty("keySecurity", keySecurity))
        server.execute()
    }

    fun setEstacao(estacao: Estacao?) {
        val server = Kserver(NAME_SAPCE, SetStationMethod, URL)
        server.addObserver(this)
        server.addProperty(KserverProperty("station", estacao))
        server.execute()
    }

    fun setEquipamento(equipamento: Equipamento?) {
        val server = Kserver(NAME_SAPCE, SetEquipmentMethod, URL)
        server.addObserver(this)
        server.addProperty(KserverProperty("equipment", equipamento))
        server.execute()
    }

    fun getEquipamento(numSerie: String?, keySecurity: String?) {
        val server = Kserver(NAME_SAPCE, GetEquipmentMethod, URL)
        server.addObserver(this)
        server.addProperty(KserverProperty("numserie", numSerie))
        server.addProperty(KserverProperty("keySecurity", keySecurity))
        server.execute()
    }

    fun sendMail(softname: String?, token: String?, subject: String?, body: String?) {
        val server = Kserver(NAME_SAPCE, SendMailMethod, URL)
        server.addObserver(this)
        server.addProperty(KserverProperty("softname", softname))
        server.addProperty(KserverProperty("token", token))
        server.addProperty(KserverProperty("subject", subject))
        server.addProperty(KserverProperty("body", body))
        server.execute()
    }

    fun setHChoice(hChoice: Hchoice?) {
        val server = Kserver(NAME_SAPCE, SetHChoiceMethod, URL)
        server.addObserver(this)
        server.addProperty(KserverProperty("hchoice", hChoice))
        server.execute()
    }

    fun getAcessoDicas(numSerie: String?, keySecurity: String?) {
        val server = Kserver(NAME_SAPCE, GetAcessoDicasMethod, URL)
        server.addObserver(this)
        server.addProperty(KserverProperty("numserie", numSerie))
        server.addProperty(KserverProperty("keySecurity", keySecurity))
        server.execute()
    }

    fun getAcessoChat(numSerie: String?, keySecurity: String?) {
        val server = Kserver(NAME_SAPCE, GetAcessoChatMethod, URL)
        server.addObserver(this)
        server.addProperty(KserverProperty("numserie", numSerie))
        server.addProperty(KserverProperty("keySecurity", keySecurity))
        server.execute()
    }

    fun doLicenseHardware(
        numSerie: String?, keySecurity: String?,
        cliente: Cliente?, data: String?
    ) {
        val server = Kserver(NAME_SAPCE, LicenseHardwareMethod, URL)
        server.addObserver(this)
        server.addProperty(KserverProperty("numserie", numSerie))
        server.addProperty(KserverProperty("keySecurity", keySecurity))
        server.addProperty(KserverProperty("cliente", cliente))
        server.addProperty(KserverProperty("data", data))
        server.execute()
    }

    fun licenseSoftware(
        numSerie: String?, keySecurity: String?,
        mac: String?, vol: String?, swid: Int?,
        cliente: Cliente?, data: Date?
    ) {
        val server = Kserver(NAME_SAPCE, LicencaSoftwareMethod, URL)
        server.addObserver(this)
        server.addProperty(KserverProperty("numserie", numSerie))
        server.addProperty(KserverProperty("keySecurity", keySecurity))
        server.addProperty(KserverProperty("mac", mac))
        server.addProperty(KserverProperty("vol", vol))
        server.addProperty(KserverProperty("cliente", cliente))
        server.addProperty(KserverProperty("data", data))
        server.execute()
    }

    fun whatIsNew(plaid: Int?, data: String?) {
        val server = Kserver(NAME_SAPCE, WhatIsNewMethod, URL)
        server.addObserver(this)
        server.addProperty(KserverProperty("plaid", plaid))
        server.addProperty(KserverProperty("data", data))
        server.execute()
    }

    val plataformas: Unit
        get() {
            val server = Kserver(NAME_SAPCE, getPlataformasMethod, URL)
            server.addObserver(this)
            server.execute()
        }

    fun getLastVersion() {
        val server = Kserver(NAME_SAPCE, getLastVersionMethod, URL)
        server.addObserver(this)
        server.execute()
    }

    fun getLastResponse(): KserverResponse? {
        return lastResponse
    }

    fun getCliente(): Cliente? {
        return cliente
    }

    fun getEquipamento(): Equipamento? {
        return equipamento
    }

    fun getPlataformaList(): PlataformaList? {
        return plataformaList
    }

    fun getLicenseHardware(): LicenseHardware? {
        return licenseHardware
    }

    override fun update(o: Observable, arg: Any) {
        lastResponse = arg as KserverResponse
        logger.d(
            TAG, "response (" + lastResponse?.methodName.toString() + "): " +
                    lastResponse?.obj.toString()
        )
        setChanged()
        if (lastResponse?.methodName === GetClientMethod) {
            cliente = Cliente(lastResponse?.obj as SoapObject)
            notifyObservers(cliente)
        } else if (lastResponse?.methodName as String === GetEquipmentMethod) {
            equipamento = Equipamento(lastResponse?.obj as SoapObject)
            notifyObservers(equipamento)
        } else if (lastResponse?.methodName as String === getPlataformasMethod) {
            plataformaList = PlataformaList(lastResponse?.obj as Vector<*>)
            notifyObservers(plataformaList)
        } else if (lastResponse?.methodName as String === LicenseHardwareMethod) {
            licenseHardware = LicenseHardware(lastResponse?.obj as SoapObject)
            notifyObservers(licenseHardware)
        } else if (lastResponse?.methodName as String === getLastVersionMethod) {
            lastVersion = LastVersionResponse(lastResponse?.obj.toString())
            notifyObservers(lastVersion)
        } else if (lastResponse?.methodName as String === SetClientMethod) {
            setClientResponse = SetClientResponse(lastResponse?.obj.toString())
            notifyObservers(setClientResponse)
        } else if (lastResponse?.methodName as String === SetEquipmentMethod) {
            setEquipmentResponse = SetEquipmentResponse(lastResponse?.obj.toString())
            notifyObservers(setEquipmentResponse)
        } else {
            notifyObservers(lastResponse)
        }
    }

    companion object {
        private const val TAG = "KserverRasther"
        private const val NAME_SAPCE = "urn:rastherserver"
        private const val SetClientMethod = "SetClient"
        private const val GetClientMethod = "GetClient"
        private const val SetStationMethod = "SetStation"
        private const val SetEquipmentMethod = "SetEquipment"
        private const val GetEquipmentMethod = "GetEquipment"
        private const val SendMailMethod = "SendMail"
        private const val SetHChoiceMethod = "SetHChoice"
        private const val GetAcessoDicasMethod = "GetAcessoDicas"
        private const val GetAcessoChatMethod = "GetAcessoChat"
        private const val LicenseHardwareMethod = "LicenceHardware"
        private const val LicencaSoftwareMethod = "LicencaSoftware"
        private const val WhatIsNewMethod = "WhatIsNew"
        private const val getPlataformasMethod = "getPlataformas"
        private const val getLastVersionMethod = "getLastVersion"
        private const val URL = "https://atualize.tecnomotor.com.br/knowledge/kserver_rasther6.php"
    }
}