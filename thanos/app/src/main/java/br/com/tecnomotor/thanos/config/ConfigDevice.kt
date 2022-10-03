package br.com.tecnomotor.thanos.config

import android.content.Context
import br.com.tecnomotor.thanos.model.menu.Montadora
import br.com.tecnomotor.thanos.util.ConvertClass
import java.time.LocalDate
import java.util.*

class ConfigDevice(context: Context) : ConfigApp(context, "ConfigDevice") {

    private val cFirstConnectionMade = "FirstConnectionMade"
    private val cConnectionDate = "ConnectionDate"
    private val cDeviceMac = "DeviceMac"
    private val cSerialNumber = "SerialNumber"
    private val cPlatform = "Platform"
    private val cVersion = "Version"
    private val cBootId = "BootId"
    private val cMakers = "Makers"
    private val cMakersText = "MakersText"
    private val cFirmwareVersion = "FirmwareVersion"
    private val cNumberOfConnectionAttempts = "NumberOfConnectionAttempts"

    //Dias sem conexão com a VCI permitidos
    private val daysWithoutConnectionToVCIAllowed = 7

    companion object {
        private var instance: ConfigDevice? = null

        fun getInstance(context: Context? = null):ConfigDevice {
            if (instance == null)
                if (context == null)
                    throw  Exception("O contexto deve ser enviado pelo menos na primeira vez que o método getInstance(contexto) for chamado")
                else
                    instance = ConfigDevice(context.applicationContext)
            return instance!!
        }
    }

    /**
     * Define que foi realizada a primeira conexão de alguma VCI com o oplicativo
     */
    fun setFirstConnectionMade(value: Boolean): ConfigDevice {
        edit().putBoolean(cFirstConnectionMade, value).apply()
        return this
    }

    /**
     * Retorna se já foi realizada uma primeira conexão com alguma VCI
     */
    fun firstConnectionMade():Boolean {
        return sharedPreferences.getBoolean(cFirstConnectionMade, false)
    }

    /**
     * Utilizando esse método é salvo a data e hora atual, deve ser utilizado todas as vezes que o
     * APP realiza uma conexão com alguma VCI
     */
    fun setConnectionDate(): ConfigDevice {
        edit().putString(cConnectionDate, LocalDate.now().toString()).apply()
        return this
    }

    /**
     * Retorna a data e hora da última conexão com alguma VCI
     */
    fun getLastConnectionDate(): LocalDate {
        val defaultDate = "0000-01-01"
        val lastConnectionDateStr = if (sharedPreferences.contains(cConnectionDate))
            sharedPreferences.getString(cConnectionDate, defaultDate)!!
        else defaultDate
        return LocalDate.parse(lastConnectionDateStr)
    }

    /**
     * Retorna a quantidade de dias sem conexão com alguma VCI
     */
    fun getDaysWithoutConnection():Int {
        val lastConnectionDate = getLastConnectionDate()
        val currentDate = LocalDate.now()
        return currentDate.compareTo(lastConnectionDate)
    }

    /**
     * Devo me conectar com a VCI agora?
     * Utilize esse método para verificação se é necessário realizar a conexão com a VCI, ele deve
     * ser utilizado como o modo desconectado liberando algumas telas que não exigem conexão com a
     * VCI
     */
    fun shouldIConnectToVCINow(): Boolean {
        return getDaysWithoutConnection() > daysWithoutConnectionToVCIAllowed
    }

    /**
     * Define o MAC Address da última VCI conectada
     */
    fun setDeviceMac(value: String): ConfigDevice {
        edit().putString(cDeviceMac, value).apply()
        return this
    }

    /**
     * Retorna o MAC Address da última VCI conectada
     */
    fun getDeviceMac(): String {
        return if (sharedPreferences.contains(cDeviceMac)) sharedPreferences.getString(cDeviceMac, "")!! else ""
    }

    /**
     * Define o número de série da última VCI conectada
     */
    fun setSerialNumber(value: String): ConfigDevice {
        edit().putString(cSerialNumber, value).apply()
        return this
    }

    /**
     * Retorna o número de série da última VCI conectada
     */
    fun getSerialNumber(): String {
        return if (sharedPreferences.contains(cSerialNumber)) sharedPreferences.getString(
            cSerialNumber, "")!! else ""
    }

    /**
     * Define a plataforma da última VCI conectada
     */
    fun setPlatform(value: Int): ConfigDevice {
        edit().putInt(cPlatform, value).apply()
        return this
    }

    /**
     * Retorna a plataforma da última VCI conectada
     */
    fun getPlatform(): Int {
        return sharedPreferences.getInt(cPlatform, -1)
    }

    /**
     * Define a versão da última VCI conectada
     */
    fun setVersion(value: Int): ConfigDevice {
        edit().putInt(cVersion, value).apply()
        return this
    }

    /**
     * Retorna a versão da última VCI conectada
     */
    fun getVersion(): Int {
        return sharedPreferences.getInt(cVersion, -1)
    }

    /**
     * Define o Boot ID da última VCI conectada
     */
    fun setBootId(value: Int): ConfigDevice {
        edit().putInt(cBootId, value).apply()
        return this
    }

    /**
     * Retorna o boot ID da última VCI conectada
     */
    fun getBootId(): Int {
        return sharedPreferences.getInt(cBootId, -1)
    }

    /**
     * Define as montadoras em String da última VCI conectada
     */
    fun setIdMontadorasHabilitadas(value: String): ConfigDevice {
        edit().putString(cMakers, value.trim()).apply()
        return this
    }

    /**
     * Retorna as montadoras em String da última VCI conectada
     */
    fun getIdMontadorasHabilitadas(): String {
        return if (sharedPreferences.contains(cMakers)) sharedPreferences.getString(
            cMakers, "")!! else ""
    }

    /**
     * Retorna a lista de montadoras da última VCI conectada
     */
    fun getListIdMontadoraHabilitada(): ArrayList<Long> {
        val idMontadorasHabilitadas = this.getIdMontadorasHabilitadas()
        val listIdMontadoraHabilitada = arrayListOf<Long>()
        if (idMontadorasHabilitadas.isNotBlank() && (idMontadorasHabilitadas.isNotEmpty())) {
            idMontadorasHabilitadas.split(",").forEach { idMontadora ->
                listIdMontadoraHabilitada.add(idMontadora.trim().toLong())
            }
        }
        return listIdMontadoraHabilitada
    }

    fun setListMontadorasHabilitadas(value: String):ConfigDevice {
        edit().putString(cMakersText, value).apply()
        return this
    }

    fun getListMontadorasHabilitadas():Array<Montadora> {
        return try {
            val value = if (sharedPreferences.contains(cMakersText)) sharedPreferences.getString(
                cMakersText, ""
            )!! else ""
            val lista: Array<Montadora> = ConvertClass.jsonToClass(
                value,
                Array<Montadora>::class.java.name
            ) as Array<Montadora>
            lista
        } catch (e:Exception) {
            arrayOf()
        }
    }

    /**
     * Define a versão do firmware da última VCI conectada
     */
    fun setFirmwareVersion(value: String): ConfigDevice {
        edit().putString(cFirmwareVersion, value).apply()
        return this
    }

    /**
     * Retorna a versão do firmware da última VCI conectada
     */
    fun getFirmwareVersion(): String {
        return if (sharedPreferences.contains(cFirmwareVersion)) sharedPreferences.getString(
            cFirmwareVersion, "")!! else ""
    }

    /**
     * Define o número de tentativas para realizar a conexão com o bluetooth
     */
    fun setNumberOfConnectionAttempts(value: Int): ConfigDevice {
        edit().putInt(cNumberOfConnectionAttempts, value).apply()
        return this
    }

    /**
     * Obtem o número de tentativas para realizar a conexão com o bluetooth
     *
     *     Por padrão o número de tentativas é 3
     */
    fun getNumberOfConnectionAttempts(): Int {
        return if (sharedPreferences.contains(cNumberOfConnectionAttempts))
            sharedPreferences.getInt(cNumberOfConnectionAttempts, 3)
        else 3
    }


}
