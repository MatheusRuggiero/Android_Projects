package br.com.tecnomotor.rasther

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.tecnomotor.bluetoothserial.DiscoveryStatus
import br.com.tecnomotor.serialport.Scannable
import kotlinx.coroutines.*
import java.io.IOException
import java.lang.Exception


//Repository modules handle data operations.
// They provide a clean API so that the rest of the app can retrieve this data easily.
// They know where to get the data from and what API calls to make when data is updated.
// You can consider repositories to be mediators between different data sources,
// such as persistent models, web services, and caches.
//https://developer.android.com/jetpack/guide#fetch-data


/**
 * @Atuhor Matheus_Ruggiero
 */
@InternalCoroutinesApi
@DelicateCoroutinesApi
class RastherRepository(): Scannable {

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private lateinit var INSTANCE: RastherRepository
        private fun isItInitialized() = this::INSTANCE.isInitialized
        fun getInstance(context: Context? = null): RastherRepository {
            if ((!isItInitialized()) && (context == null))
                throw java.lang.Exception("O contexto deve ser enviado pelo menos na primeira vez que o método getInstance(contexto) for chamado")
            else if (context != null)
                INSTANCE = RastherRepository(context)
            return INSTANCE
        }
    }

    lateinit var factory: RastherFactory
    lateinit var rasther: Rasther
    private val deviceConnection = DevicesConnection()
    val statusConnectionLiveData = MutableLiveData<DevicesConnection>()
    private var connectJob = GlobalScope.launch(context = Dispatchers.IO) {  }//só pra inicializar o job
    var numberOfConnectionAttempts: Int = 3

    constructor(context: Context) : this() {
        try {
            factory = RastherFactory(context)
        } catch (e: Exception) {
            Log.e(this.javaClass.simpleName, "Exception init factory:: $e")
        }
    }

    fun connect(mac: String){
        if (!connectJob.isActive) {
            disconnect()
            connectJob = connectJobCreator(mac)
        }
    }

    fun disconnect() {
        if ((::rasther.isInitialized) && (rasther.isConnected())) {
            rasther.disconnect()
            deviceConnection.setStatus(DevicesConnection.DevicesStatus.DISCONNECTED)
            statusConnectionLiveData.postValue(deviceConnection)
        }
    }

    private fun connectJobCreator(address: String): Job {
        if (!this::factory.isInitialized)
            return GlobalScope.launch {
                deviceConnection.setStatus(
                    DevicesConnection.DevicesStatus.FAIL,
                    "Realizar o pareamento",
                    "Erro: Scan não inicializado"
                )
                statusConnectionLiveData.postValue(deviceConnection)
            }
        var connected: Boolean = false
        var count = 1
        Log.i(this.javaClass.simpleName, "Connecting by $address")
        deviceConnection.setStatus(DevicesConnection.DevicesStatus.CONNECTING)
        statusConnectionLiveData.postValue(deviceConnection)
        return GlobalScope.launch {
            while (!connected && count <= numberOfConnectionAttempts){
                try {
                    Log.i(this.javaClass.simpleName, "Connect tentative $count :: $address")
                    rasther = factory.createRastherDevice(address)
                    rasther.setConnet(true)
                    connected = true
                    deviceConnection.setStatus(DevicesConnection.DevicesStatus.CONNECTED,
                        "Dispositivo conectado")
                }catch(e:IndexOutOfBoundsException){
                    Log.e(this@RastherRepository.javaClass.simpleName, "IndexOutOfBoundsException: ${e.message}")
                    deviceConnection.setStatus(DevicesConnection.DevicesStatus.FAIL,
                        e.message.toString(),"Erro ao encontrar o dispositivo selecionado")
                }catch(e: IOException){
                    Log.e(this@RastherRepository.javaClass.simpleName, "IOException: ${e.message}")
                    deviceConnection.setStatus(DevicesConnection.DevicesStatus.FAIL,
                        e.message.toString(),"Erro ao conectar com o disposistivo selecionado")
                }
                delay(1000)
                count++
            }
            statusConnectionLiveData.postValue(deviceConnection)
        }
    }

    fun clearMessages() {
        deviceConnection.setStatus(DevicesConnection.DevicesStatus.NONE)
        statusConnectionLiveData.postValue(deviceConnection)
    }

    override fun startDiscovery(): Boolean {
        return if (this::factory.isInitialized)
         factory.startDiscovery() else false
    }

    override fun isDiscovering(): Boolean {
        return if (this::factory.isInitialized)
            factory.isDiscovering() else false
    }

    override fun cancelDiscovery() {
        if (this::factory.isInitialized)
            factory.cancelDiscovery()
    }

    override fun getDiscoveryStatus(): LiveData<DiscoveryStatus>? {
        return factory.getDiscoveryStatus()
    }

    override fun isBTEnabled(): Boolean {
        return if (this::factory.isInitialized)
                factory.isBTEnabled() else false
    }

    fun getDeviceList():RastherList {
        return factory.getDevicesList()
    }
}