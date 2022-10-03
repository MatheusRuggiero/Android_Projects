package br.com.tecnomotor.thanos.service.update

//import com.eos.rastherandroid.RastherDefaultActivity
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.os.Handler
import br.com.tecnomotor.logger.Logger
import br.com.tecnomotor.thanos.service.LicenseHardware
import br.com.tecnomotor.thanos.service.soap.KserverRasther
import java.util.*

class AutoEnable(activity: Activity/*, bluetoothService: BluetoothService*/) : Observable(), Observer {
    val logger: Logger = Logger(showLog = true, appName = "Thanos")
//    private val activity: Activity
//    private val sharedPreferences: SharedPreferences
//    private val bluetoothService: BluetoothService
    private var bluetoothAdapter: BluetoothAdapter? = null
    private val serverRasther: KserverRasther = KserverRasther() // soap
    var licenseHardware: LicenseHardware? = null
    var boolLicenseHardware = false
    private var licenseMontadoras = ""
    private var licenseVersao = ""
    private var serialNumber = ""
    private var md5SerialNumber = ""
    private var seedDiag: ArrayList<String>? = null
    private var seedLicense: ArrayList<String>? = null
    private var idxOperacao: Int = 0
    val tipoAleatorio = -100
    private val updtNotifier = UpdateNotifier(tipoAleatorio)

    /**
     * Atualiza o status em andamento. Ou seja, quando já começou a atualização e ainda não finalizou
     * @param status identificador do status, conforme definido em UpdateNotifier
     */
    private fun atualizaStatusObserver(status: Int) {
        updtNotifier.status = status
        updtNotifier.progresso = updtNotifier.progresso + stepProgress
        setChanged()
        notifyObservers(updtNotifier)
    }

    fun startAutomaticEnabling() {
//        if (!Utils.isNetworkAvailable(activity)) {
//            logger.e(TAG, "sem rede")
//            updtNotifier.codigoErro = UpdateNotifier.Companion.ERRO_SEM_REDE
//            setChanged()
//            notifyObservers(updtNotifier)
//            return
//        }
//        atualizaStatusObserver(UpdateNotifier.Companion.STATUS_INICIANDO)
//        var isNotEnabledIsConnected = false
//        bluetoothService.resumeSending()
//        bluetoothService.setHandler(handler)
//        bluetoothService.setProtocolStarted(false)
//        if (bluetoothAdapter.isEnabled()
//            && sharedPreferences.contains(RastherDefaultActivity.PREFERENCES_BLUETOOTH)
//            && bluetoothService.getConnectionState() !== BluetoothConnection.STATE_CONNECTED
//        ) {
//            logger.i(TAG, "BluetoothAdapter.isEnabled")
//            if (bluetoothService.getState() !== BluetoothService.STATE_INITIALIZED) {
//                logger.i(TAG, "BluetoothService Initializing...")
//                bluetoothService.initialize(handler, bluetoothAdapter)
//            } else {
//                bluetoothService.setHandler(handler)
//                logger.i(TAG, "BluetoothService.isInitialized.")
//            }
//            logger.i(TAG, "BluetoothService.connect().")
//            bluetoothService.connect()
//            isNotEnabledIsConnected = false
//        } else {
//            logger.i(TAG, "BluetoothAdapter.isNotEnabled.")
//            if (bluetoothService.getConnectionState() === BluetoothConnection.STATE_CONNECTED) {
//                logger.i(TAG, "BluetoothService.isConnected.")
//                isNotEnabledIsConnected = true
//            } else {
//                logger.i(TAG, "BluetoothService.isNotConnected.")
//                setChanged()
//                updtNotifier.codigoErro = UpdateNotifier.Companion.ERRO_DE_COMUNICACAO
//                notifyObservers(updtNotifier)
//                return
//            }
//        }
//        idxOperacao = REQUEST_START_COMM
//        // se já estava conectado então não entrará no handle
//        if (isNotEnabledIsConnected) {
//            proximaOperacao(idxOperacao) // A3
//        }
    }

    /**
     * Handler utilizado para tratar mensagens que chegam do Rasther
     */
    private val handler: Handler = object : Handler() {
//        private var messageHeader // antigo data
//                : String? = null
//        var receivedMessage: ArrayList<String>? = null
//        override fun handleMessage(msg: Message) {
//            //Logger.i(TAG, "Handle BluetoothService msg: " + bluetoothService.getReceivedMessageName(msg.what));
//            when (msg.what) {
//                BluetoothService.MESSAGE_CONNECTED -> proximaOperacao(idxOperacao) //idxOperacao = REQUEST_START_COMM = 1;
//                BluetoothService.MESSAGE_READ -> {
//                    messageHeader =
//                        bluetoothService.cmd.getData2(msg.obj as ArrayList<String?>).get(0)
//                    //Logger.d(TAG, "Handle data: " + data.toString());
//                    receivedMessage = msg.obj as ArrayList<String>
//                    //Logger.d(TAG, "getData(receivedMessage): " + bluetoothService.cmd.getData(receivedMessage).toString());
//                    //Logger.d(TAG, "receivedMessage: " + receivedMessage.toString());
//                    if (idxOperacao == REQUEST_START_COMM && messageHeader == "41") {
//                        // iniciou comunicação
//                        // sleep para o usuário ler o status
//                        try {
//                            Thread.sleep(100)
//                        } catch (e: InterruptedException) {
//                            e.printStackTrace()
//                        }
//                        proximaOperacao(idxOperacao + 1)
//                    } else if (idxOperacao == REQUEST_VERSION && messageHeader == "45") {
//                        //Logger.i(TAG, "Firmware: " + bluetoothService.cmd.getFirmwareVersion(receivedMessage));
//                        UpgradeActivity.versaoFirmware =
//                            bluetoothService.cmd.getFirmwareVersion(receivedMessage)
//                                .replace(".", "").replace(",", "") // retira pontos e vírgulas;
//                        proximaOperacao(idxOperacao + 1)
//                    } else if (idxOperacao == REQUEST_SERIAL_NUMBER && messageHeader == "61") {
//                        serialNumber = bluetoothService.cmd.getSerialNumber(
//                            bluetoothService.cmd.getData2(receivedMessage)
//                        )
//                        md5SerialNumber = Md5Helper.getMD5EncryptedString(serialNumber)
//                        proximaOperacao(idxOperacao + 1)
//                    } else if (idxOperacao == REQUEST_SECURITY_DIAG_1 && messageHeader == "67") {
//                        seedDiag = receivedMessage
//                        proximaOperacao(idxOperacao + 1)
//                    } else if (idxOperacao == REQUEST_SECURITY_DIAG_2 && messageHeader == "67") {
//                        proximaOperacao(idxOperacao + 1)
//                    } else if (idxOperacao == REQUEST_SECURITY_LICENSE_1 && messageHeader == "67") {
//                        seedLicense = receivedMessage
//                        proximaOperacao(idxOperacao + 1)
//                    } else if (idxOperacao == REQUEST_SECURITY_LICENSE_2 && messageHeader == "67") {
//                        proximaOperacao(idxOperacao + 1)
//                    } else if (idxOperacao == REQUEST_WRITE_VERSION) {
//                        proximaOperacao(idxOperacao + 1)
//                    } else if (idxOperacao == REQUEST_READ_VERSION && messageHeader == "6A") {
//                        val version: String =
//                            java.lang.String.valueOf(bluetoothService.cmd.getVersion(receivedMessage))
//                        val platform: String = java.lang.String.valueOf(
//                            bluetoothService.cmd.getPlatform(receivedMessage)
//                        )
//                        UpgradeActivity.versaoHabilitada = version
//                        //Logger.i(TAG, "Versão habilitada: " + bluetoothService.cmd.getVersion(receivedMessage));
//                        //Logger.i(TAG, "Plataforma       : " + bluetoothService.cmd.getPlatform(receivedMessage));
//                        sharedPreferences.edit()
//                            .putString(RastherDefaultActivity.PREFERENCES_VERSION, version).commit()
//                        sharedPreferences.edit()
//                            .putString(RastherDefaultActivity.PREFERENCES_PLATFORM, platform)
//                            .commit()
//                        proximaOperacao(idxOperacao + 1)
//                    } else if (idxOperacao == REQUEST_WRITE_MAKER) {
//                        proximaOperacao(idxOperacao + 1)
//                    } else if (idxOperacao == REQUEST_READ_MAKER) {
//                        val listaMontadorasHexa: ArrayList<String> =
//                            bluetoothService.cmd.getData(receivedMessage)
//                        val gson = Gson()
//                        val json: String = gson.toJson(listaMontadorasHexa)
//                        sharedPreferences.edit()
//                            .putString(RastherDefaultActivity.PREFERENCES_ENABLED_MAKERS, json)
//                            .commit()
//                        sharedPreferences.edit()
//                            .putBoolean(
//                                RastherDefaultActivity.PREFERENCES_HAS_FUNCIONS_MAKER,
//                                bluetoothService.cmd.getDataInt(receivedMessage).contains(113)
//                            ).commit()
//                        sharedPreferences.edit()
//                            .putBoolean(
//                                RastherDefaultActivity.PREFERENCES_HAS_SHOCK_MAKER,
//                                bluetoothService.cmd.getDataInt(receivedMessage).contains(112)
//                            ).commit()
//                        UpgradeActivity.montadorasHabilitadas =
//                            Utils.getAutoMakerString(listaMontadorasHexa)
//                        proximaOperacao(idxOperacao + 1)
//                    } else if (idxOperacao == REQUEST_SECURITY_DIAG_3) {
//                        seedDiag = receivedMessage
//                        proximaOperacao(idxOperacao + 1)
//                    } else if (idxOperacao == REQUEST_SECURITY_DIAG_4) {
//                        proximaOperacao(idxOperacao + 1)
//                    }
//                }
//                BluetoothService.MESSAGE_CONNECTION_LOST, BluetoothService.MESSAGE_XML_TIME_OUT, BluetoothService.MESSAGE_TIMEOUT, BluetoothService.MESSAGE_TOAST -> {
//                    logger.w(TAG, bluetoothService.getReceivedMessageName(msg.what))
//                    setChanged()
//                    updtNotifier.codigoErro = UpdateNotifier.Companion.ERRO_DE_COMUNICACAO
//                    notifyObservers(updtNotifier)
//                }
//            }
//        }
    }

    private fun proximaOperacao(value: Int) {
//        idxOperacao = value
//        // quando dá erro e termina então não processa mais as notificações que chegam do observer
//        if (updtNotifier.status == UpdateNotifier.Companion.STATUS_TERMINADO) return
//        logger.w(TAG, "proximaOperacao: " + getNomeProximaOperacao(idxOperacao))
//        when (idxOperacao) {
//            REQUEST_CONNECT_RASTHER ->                // TODO: colocar aqui início da comunicação com o rasther
//                atualizaStatusObserver(UpdateNotifier.Companion.STATUS_VERIFICANDO)
//            REQUEST_START_COMM -> {
//                bluetoothService.cmd.startCommunication()
//                atualizaStatusObserver(UpdateNotifier.Companion.STATUS_VERIFICANDO)
//                MeasuresActivity.ClearCrypt()
//            }
//            REQUEST_VERSION -> {
//                atualizaStatusObserver(UpdateNotifier.Companion.STATUS_VERIFICANDO)
//                bluetoothService.cmd.getVersion() // cmd "05" firmware
//            }
//            REQUEST_SERIAL_NUMBER -> {
//                atualizaStatusObserver(UpdateNotifier.Companion.STATUS_VERIFICANDO)
//                bluetoothService.cmd.serialNumberRead() // cmd "21"
//            }
//            REQUEST_SECURITY_DIAG_1 -> {
//                atualizaStatusObserver(UpdateNotifier.Companion.STATUS_VERIFICANDO)
//                bluetoothService.cmd.security() // cmd "27"
//            }
//            REQUEST_SECURITY_DIAG_2 -> {
//                atualizaStatusObserver(UpdateNotifier.Companion.STATUS_VERIFICANDO)
//                val keyDiag: ArrayList<String> = bluetoothService.cmd.generateKey(
//                    seedDiag,
//                    serialNumber,
//                    CommunicationProtocol.SECURITY_DIAG
//                )
//                //Logger.i(TAG, "Security diag key: " + keyDiag.toString());
//                bluetoothService.write(keyDiag)
//                MeasuresActivity.SetCrypt(keyDiag)
//            }
//            REQUEST_SECURITY_LICENSE_1 -> {
//                atualizaStatusObserver(UpdateNotifier.Companion.STATUS_VERIFICANDO)
//                bluetoothService.cmd.security() // cmd "27"
//            }
//            REQUEST_SECURITY_LICENSE_2 -> {
//                atualizaStatusObserver(UpdateNotifier.Companion.STATUS_VERIFICANDO)
//                // sleep para o usuário ler o status
//                try {
//                    Thread.sleep(1000)
//                } catch (e: Exception) {
//                }
//                val keyLicense: ArrayList<String> = bluetoothService.cmd.generateKey(
//                    seedLicense,
//                    serialNumber,
//                    CommunicationProtocol.SECURITY_LICENSE
//                )
//                bluetoothService.write(keyLicense)
//            }
//            REQUEST_SOAP_LICENSE_HARDWARE -> {
//                atualizaStatusObserver(UpdateNotifier.Companion.STATUS_BAIXANDO)
//                // sleep para o usuário ler o status
//                try {
//                    Thread.sleep(1000)
//                } catch (e: Exception) {
//                }
//                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
//                val date = sdf.format(Date())
//                boolLicenseHardware = false
//                serverRasther.doLicenseHardware(
//                    serialNumber,
//                    md5SerialNumber,
//                    UpgradeActivity.clienteAtualizado,
//                    date
//                )
//            }
//            REQUEST_WRITE_VERSION -> {
//                atualizaStatusObserver(UpdateNotifier.Companion.STATUS_ATUALIZANDO)
//                // sleep para o usuário ler o status
//                try {
//                    Thread.sleep(1000)
//                } catch (e: Exception) {
//                }
//                bluetoothService.cmd.versionWrite(licenseVersao)
//            }
//            REQUEST_READ_VERSION -> {
//                atualizaStatusObserver(UpdateNotifier.Companion.STATUS_ATUALIZANDO)
//                // sleep para o usuário ler o status
//                try {
//                    Thread.sleep(1000)
//                } catch (e: Exception) {
//                }
//                bluetoothService.cmd.versionRead() // "2A" versão habilitada e plataforma
//            }
//            REQUEST_WRITE_MAKER -> {
//                atualizaStatusObserver(UpdateNotifier.Companion.STATUS_ATUALIZANDO)
//                // sleep para o usuário ler o status
//                try {
//                    Thread.sleep(1000)
//                } catch (e: Exception) {
//                }
//                bluetoothService.cmd.makerWrite(licenseMontadoras)
//            }
//            REQUEST_READ_MAKER -> {
//                atualizaStatusObserver(UpdateNotifier.Companion.STATUS_ATUALIZANDO)
//                // sleep para o usuário ler o status
//                try {
//                    Thread.sleep(1000)
//                } catch (e: Exception) {
//                }
//                bluetoothService.cmd.getEnableMakers()
//            }
//            REQUEST_SECURITY_DIAG_3 -> {
//                atualizaStatusObserver(UpdateNotifier.Companion.STATUS_FINALIZANDO)
//                // sleep para o usuário ler o status
//                try {
//                    Thread.sleep(1000)
//                } catch (e: Exception) {
//                }
//                bluetoothService.cmd.security() // cmd "27"
//            }
//            REQUEST_SECURITY_DIAG_4 -> {
//                atualizaStatusObserver(UpdateNotifier.Companion.STATUS_FINALIZANDO)
//                val keyDiagSecond: ArrayList<String> = bluetoothService.cmd.generateKey(
//                    seedDiag,
//                    serialNumber,
//                    CommunicationProtocol.SECURITY_DIAG
//                )
//                // sleep para o usuário ler o status
//                try {
//                    Thread.sleep(1000)
//                } catch (e: Exception) {
//                }
//                bluetoothService.write(keyDiagSecond)
//                MeasuresActivity.SetCrypt(keyDiagSecond)
//            }
//            REQUEST_FINISH -> {
//                // sleep para o usuário ler o status
//                try {
//                    Thread.sleep(1000)
//                } catch (e: Exception) {
//                }
//                bluetoothService.cmd.stopCommunication()
//                atualizaStatusObserver(UpdateNotifier.Companion.STATUS_TERMINADO)
//            }
//        }
    }

    /**
     * Devolve uma string com o nome do comando da máquina de estados.
     * @param commandInt número que representa o comando.
     * @return String com nome do comando.
     */
    private fun getNomeProximaOperacao(commandInt: Int): String {
        var command = ""
        command = when (commandInt) {
            REQUEST_CONNECT_RASTHER -> "REQUEST_CONNECT_RASTHER"
            REQUEST_START_COMM -> "REQUEST_START_COMM"
            REQUEST_VERSION -> "REQUEST_VERSION"
            REQUEST_SERIAL_NUMBER -> "REQUEST_SERIAL_NUMBER"
            REQUEST_SECURITY_DIAG_1 -> "REQUEST_SECURITY_DIAG_1"
            REQUEST_SECURITY_DIAG_2 -> "REQUEST_SECURITY_DIAG_2"
            REQUEST_SECURITY_LICENSE_1 -> "REQUEST_SECURITY_LICENSE_1"
            REQUEST_SECURITY_LICENSE_2 -> "REQUEST_SECURITY_LICENSE_2"
            REQUEST_SOAP_LICENSE_HARDWARE -> "REQUEST_SOAP_LICENSE_HARDWARE"
            REQUEST_WRITE_VERSION -> "REQUEST_WRITE_VERSION"
            REQUEST_READ_VERSION -> "REQUEST_READ_VERSION"
            REQUEST_WRITE_MAKER -> "REQUEST_WRITE_MAKER"
            REQUEST_READ_MAKER -> "REQUEST_READ_MAKER"
            REQUEST_SECURITY_DIAG_3 -> "REQUEST_SECURITY_DIAG_3"
            REQUEST_SECURITY_DIAG_4 -> "REQUEST_SECURITY_DIAG_4"
            REQUEST_FINISH -> "REQUEST_FINISH"
            else -> "Comando não definido! Atualizar getNomeProximaOperacao."
        }
        return command
    }

    override fun update(observable: Observable, data: Any) {
//        logger.w(TAG, "UPDATE:")
//        if (observable == null || data == null) return
//        if (observable.javaClass == KserverRasther::class.java) {
//            if (data.javaClass == LicenseHardware::class.java) {
//                licenseHardware = data as LicenseHardware
//                if (licenseHardware.getErro() !== -1) {
//                    boolLicenseHardware = true
//                    licenseVersao = licenseHardware.getVersao()
//                    licenseMontadoras = licenseHardware.getMontadora()
//                    //Logger.w(TAG, licenseVersao);
//                    proximaOperacao(idxOperacao + 1)
//                }
//            } else {
//                logger.w(TAG, "data.toString(): $data")
//            }
//        }
    }

    companion object {
        private const val TAG = "AutoEnable"

        // controle da máquina de estados - idxOperacao
        private const val REQUEST_CONNECT_RASTHER = 0
        private const val REQUEST_START_COMM = 1
        private const val REQUEST_VERSION = 2
        private const val REQUEST_SERIAL_NUMBER = 3
        private const val REQUEST_SECURITY_DIAG_1 = 4
        private const val REQUEST_SECURITY_DIAG_2 = 5
        private const val REQUEST_SECURITY_LICENSE_1 = 6
        private const val REQUEST_SECURITY_LICENSE_2 = 7
        private const val REQUEST_SOAP_LICENSE_HARDWARE = 8
        private const val REQUEST_WRITE_VERSION = 9
        private const val REQUEST_READ_VERSION = 10
        private const val REQUEST_WRITE_MAKER = 11
        private const val REQUEST_READ_MAKER = 12
        private const val REQUEST_SECURITY_DIAG_3 = 13
        private const val REQUEST_SECURITY_DIAG_4 = 14
        private const val REQUEST_FINISH = 15
        private const val maxProgress = 100
        private const val stepProgress =
            maxProgress / (REQUEST_FINISH + 1) // 100 dividido pelo número de passos
    }

    /**
     * Construtor
     */
    init {
//        bluetoothService.cmd.setAddress(
//            CommunicationProtocol.KWP_HOST_DIAG,
//            CommunicationProtocol.KWP_TARGET_DIAG
//        )
//        this.activity = activity
//        sharedPreferences =
//            activity.getSharedPreferences(RastherDefaultActivity.PREFERENCES, Context.MODE_PRIVATE)
//        this.bluetoothService = bluetoothService
//        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
//        idxOperacao = REQUEST_CONNECT_RASTHER
//        updtNotifier = UpdateNotifier(UpdateNotifier.Companion.TIPO_HABILITACAO)
//        updtNotifier.maxProgresso = maxProgress
//        setChanged() // observer
//        notifyObservers(updtNotifier)
//        serverRasther.addObserver(this)
    }
}