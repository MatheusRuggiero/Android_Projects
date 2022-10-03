package br.com.tecnomotor.thanos.service.update

//import com.eos.rastherandroid.RastherDefaultActivity
import android.app.Activity
import java.util.*

class UpdateFirmware(
    activity: Activity,
    //bluetoothService: BluetoothService,
    recuperaInterface: Boolean
) : Observable() {
//    private var serialNumber = ""
//    private var firmwareRasther = ""
//    private var firmwareXml = ""
//    private var firmwareXmlData: ArrayList<String>
//    private var seed: ArrayList<String>? = null
//    private val activity: Activity
//    private val sharedPreferences: SharedPreferences
//    private val bluetoothService: BluetoothService
//    private var bluetoothAdapter: BluetoothAdapter? = null
//    private var idxOperacao: Int
//    private var indiceXml = 0
//    private var tentativasUpgrade = 0
//    private var firmwareXmlDataSize: Int
//    private val updtNotifier: UpdateNotifier
//    private val recuperaInterface: Boolean
//
//    /**
//     * Compara a  versão do firmware do XML com uma versão enviada por parâmetro.
//     * @param firmware String com ou sem ponto separador da versão do firmware para ser comparado.
//     * @return Booleano indicando se são iguais (true) ou diferentes (false).
//     */
//    private fun comparaVersaoFirmware(firmware: String): Boolean {
//        var firmware = firmware
//        firmware = firmware.replace(".", "").replace(",", "")
//
//        //Logger.d(TAG, "Firmware Local: " + firmware);
//        //Logger.d(TAG, "Firmware XML  : " + this.firmwareXml);
//        var resultado = false
//        resultado = try {
//            firmware == firmwareXml
//        } catch (e: Exception) {
//            false
//        }
//        return resultado
//    }
//
//    /**
//     * Abre o arquivo XML contendo o firmware
//     * @throws IOException
//     */
//    // TODO mostrar mensagem de erro de quando não conseguir abrir arquivo
//    @Throws(FileNotFoundException::class, Exception::class)
//    private fun openFirmwareXml() {
//        //try {
//        firmwareXml =
//            XmlReader.getXmlVersionFirmware(Utils.getXmlParserFromFirmware(activity.getBaseContext()))
//        firmwareXml = firmwareXml.replace(".", "").replace(",", "")
//        //Logger.w(TAG, "openFirmwareXml:: firmwareXml: " + firmwareXml);
//        firmwareXmlData =
//            XmlReader.getXmlDataFirmware(Utils.getXmlParserFromFirmware(activity.getBaseContext()))
//        firmwareXmlDataSize = firmwareXmlData.size
//        updtNotifier.maxProgresso = firmwareXmlDataSize
//        setChanged()
//        notifyObservers(updtNotifier)
//
////    	} catch (Exception e) {
////    		Logger.e(TAG, e.getMessage());
////    	}
//    }
//
//    /**
//     * Realiza a atualização do firmware. Abre o arquivo xml, descriptografa e o envia para para o Rasther.
//     * @throws IOException
//     */
//    @Throws(Exception::class)
//    fun atualizaFirmware() {
//        var isNotEnabledIsConnected = false
//        indiceXml = 0
//        tentativasUpgrade = 0
//
//        // atualização forçada, já vai direto pra A2
//        if (recuperaInterface) {
//            idxOperacao = REQUEST_HOST_UPDT_ADDRESS
//        }
//        bluetoothService.cmd.setAddress(
//            CommunicationProtocol.KWP_HOST_DIAG,
//            CommunicationProtocol.KWP_TARGET_DIAG
//        )
//        try {
//            openFirmwareXml() // abre arquivo xml contendo firmware
//        } catch (   /*FileNotFoundException*/e: Exception) {
//            updtNotifier.codigoErro = UpdateNotifier.Companion.ERRO_ARQ_NAO_ENCONTRADO
//            setChanged()
//            notifyObservers(updtNotifier)
//            return
//        }
//        bluetoothService.resumeSending()
//        bluetoothService.setHandler(handler)
//        bluetoothService.setProtocolStarted(false)
//        if (bluetoothAdapter.isEnabled()
//            && sharedPreferences.contains(RastherDefaultActivity.PREFERENCES_BLUETOOTH)
//            && bluetoothService.getConnectionState() !== BluetoothConnection.STATE_CONNECTED
//        ) {
//            Logger.i(TAG, "BluetoothAdapter.isEnabled")
//            //showDialog();
//            if (bluetoothService.getState() !== BluetoothService.STATE_INITIALIZED) {
//                Logger.i(TAG, "BluetoothService Initializing...")
//                bluetoothService.initialize(handler, bluetoothAdapter)
//            } else {
//                bluetoothService.setHandler(handler)
//                Logger.i(TAG, "BluetoothService.isInitialized.")
//            }
//
////            bluetoothService.pauseSending();
////            bluetoothService.resumeSending();
//            Logger.i(TAG, "BluetoothService.connect().")
//            bluetoothService.connect()
//            isNotEnabledIsConnected = false
//        } else {
//            Logger.i(TAG, "BluetoothAdapter.isNotEnabled.")
//            if (bluetoothService.getConnectionState() === BluetoothConnection.STATE_CONNECTED) {
//                Logger.i(TAG, "BluetoothService.isConnected.")
//                isNotEnabledIsConnected = true
//                //startInitialActivity(HomeActivity.this, version, platform, serialNumber, diagType);
//            } else {
//                Logger.i(TAG, "BluetoothService.isNotConnected.")
//                //showConfigDiagParam();
//                setChanged()
//                updtNotifier.codigoErro = UpdateNotifier.Companion.ERRO_DE_COMUNICACAO
//                notifyObservers(updtNotifier)
//                return
//            }
//        }
//
//        // se já estava conectado então não entrará no handle
//        if (isNotEnabledIsConnected) {
//            if (recuperaInterface) proximaOperacao(REQUEST_HOST_UPDT_ADDRESS) // A2
//            else proximaOperacao(REQUEST_START_COMM) // A3
//        }
//    } // final atualiza firmware
//
//    /**
//     * Handler utilizado para tratar mensagens que chegam do Rasther
//     */
//    private val handler: Handler = object : Handler() {
//        private var messageHeader // antigo data
//                : String? = null
//        var receivedMessage: ArrayList<String>? = null
//        private var timeOutSendItemXml = 0 // valor em milissegundos
//        private static
//        val timeOutStep = 10
//        override fun handleMessage(msg: Message) {
//            Logger.i(
//                TAG,
//                "Handle BluetoothService msg: " + bluetoothService.getReceivedMessageName(msg.what)
//            )
//            when (msg.what) {
//                BluetoothService.MESSAGE_CONNECTED -> proximaOperacao(idxOperacao) //idxOperacao = 0;
//                BluetoothService.MESSAGE_READ -> {
//                    messageHeader =
//                        bluetoothService.cmd.getData2(msg.obj as ArrayList<String?>).get(0)
//                    //Logger.d(TAG, "Handle data: " + data.toString());
//                    receivedMessage = msg.obj as ArrayList<String>
//                    //Logger.d(TAG, "getData(receivedMessage): " + bluetoothService.cmd.getData(receivedMessage).toString());
//                    Logger.d(TAG, "receivedMessage: " + receivedMessage.toString())
//                    if (idxOperacao == REQUEST_START_COMM && messageHeader == "41") {
//                        // iniciou comunicação
//                        try {
//                            Thread.sleep(100)
//                        } catch (e: InterruptedException) {
//                            e.printStackTrace()
//                        }
//                        proximaOperacao(idxOperacao + 1)
//                    } else if (idxOperacao == REQUEST_VERSION && messageHeader == "45") {
//                        firmwareRasther = bluetoothService.cmd.getFirmwareVersion(receivedMessage)
//                        //Logger.d(TAG, "Firmware rasther: " + firmwareRasther);
//                        if (comparaVersaoFirmware(firmwareRasther)) {
//                            Logger.d(TAG, "Versão de firmware igual. NÃO ATUALIZAR!")
//                            proximaOperacao(REQUEST_STOP_COMM)
//                        } else {
//                            Logger.d(TAG, "Versão de firmware diferente. ATUALIZAR!")
//                            proximaOperacao(idxOperacao + 1)
//                        }
//                    } else if (idxOperacao == REQUEST_HOST_UPDT_ADDRESS) {
//                        if (messageHeader == "41") {
//                            try {
//                                Thread.sleep(1000)
//                            } catch (e: InterruptedException) {
//                                e.printStackTrace()
//                            }
//                            proximaOperacao(idxOperacao + 1)
//                        } else  // após terceira tentativa de atualização
//                            if (messageHeader == "42") {
//                            }
//                    } else if (idxOperacao == REQUEST_VERSION_UPDT && messageHeader == "45") {
//                        proximaOperacao(idxOperacao + 1)
//                    } else if (idxOperacao == REQUEST_SERIAL_NUMBER && messageHeader == "61") {
//                        serialNumber = bluetoothService.cmd.getSerialNumber(
//                            bluetoothService.cmd.getData2(receivedMessage)
//                        )
//                        //Logger.d(TAG, "Serial Number: " + serialNumber);
//                        proximaOperacao(idxOperacao + 1)
//                    } else if (idxOperacao == REQUEST_SECURITY_1 && messageHeader == "67") {
//                        seed = receivedMessage
//                        proximaOperacao(idxOperacao + 1)
//                    } else if (idxOperacao == REQUEST_SECURITY_2 && messageHeader == "67") {
//                        proximaOperacao(idxOperacao + 1)
//                    } else if (idxOperacao == REQUEST_UPGRADE) {
//                        if (messageHeader == "48") {
//                            if (indiceXml + 1 < firmwareXmlDataSize) {
//                                indiceXml++
//                                try {
//                                    Thread.sleep(timeOutSendItemXml.toLong())
//                                } catch (e: InterruptedException) {
//                                    e.printStackTrace()
//                                }
//                                updtNotifier.progresso = indiceXml
//                                updtNotifier.status = UpdateNotifier.Companion.STATUS_ATUALIZANDO
//                                setChanged()
//                                notifyObservers(updtNotifier)
//                                proximaOperacao(idxOperacao)
//                            } else {
//                                proximaOperacao(idxOperacao + 1)
//                                //Logger.i(TAG, "Fim envio dados firmware.");
//                            }
//                        } else {
//                            Logger.w(TAG, "Deu zebra")
//                        }
//                    } else if (idxOperacao == REQUEST_END_UPGRADE && messageHeader == "49") {
//                        proximaOperacao(idxOperacao + 1) // fecha conexão com bluetooth
//                    } else if (idxOperacao == REQUEST_STOP_COMM && messageHeader == "42") {
//                        proximaOperacao(idxOperacao + 1) // tenta comunicar em A3
//                    } else if (idxOperacao == REQUEST_TEST_A3_COMM && messageHeader == "41") {
//                        proximaOperacao(idxOperacao + 1) // update foi ok, finaliza
//                    }
//                }
//                BluetoothService.MESSAGE_CONNECTION_LOST -> {
//                    Logger.w(TAG, "Message_Connection_Lost: Conexão perdida")
//                    //showConnectionLost();
//                    updtNotifier.codigoErro = UpdateNotifier.Companion.ERRO_DE_COMUNICACAO
//                    setChanged()
//                    notifyObservers(updtNotifier)
//                }
//                BluetoothService.MESSAGE_XML_TIME_OUT -> {
//                    // TODO colocar mensagem: erro au atualizar o firmware. tenti novamenty
//                    Logger.w(TAG, "MESSAGE_XML_TIME_OUT")
//                    bluetoothService.resumeSending()
//                    idxOperacao = REQUEST_START_COMM
//                    // não está respondendo em A3
//                    if (idxOperacao == REQUEST_START_COMM) {
//                        proximaOperacao(REQUEST_HOST_UPDT_ADDRESS)
//                        Logger.e(TAG, "MESSAGE_TIMEOUT aqui")
//                    } else if (idxOperacao == REQUEST_TEST_A3_COMM) {
//                        timeOutSendItemXml += timeOutStep
//                        proximaOperacao(REQUEST_HOST_UPDT_ADDRESS)
//                        Logger.w(TAG, "MESSAGE_TIMEOUT, idxOperacao == REQUEST_TEST_A3_COMM")
//                    } else {
//                        Logger.w(TAG, "MESSAGE_TIMEOUT - erro desconhecido")
//                    }
//                }
//                BluetoothService.MESSAGE_TIMEOUT -> if (idxOperacao == REQUEST_START_COMM) {
//                    proximaOperacao(REQUEST_HOST_UPDT_ADDRESS)
//                    Logger.e(TAG, "MESSAGE_TIMEOUT aqui")
//                } else if (idxOperacao == REQUEST_TEST_A3_COMM) {
//                    timeOutSendItemXml += timeOutStep
//                    proximaOperacao(REQUEST_HOST_UPDT_ADDRESS)
//                    Logger.w(TAG, "MESSAGE_TIMEOUT, idxOperacao == REQUEST_TEST_A3_COMM")
//                } else {
//                    Logger.w(TAG, "MESSAGE_TIMEOUT - erro desconhecido")
//                }
//                BluetoothService.MESSAGE_TOAST -> {
//                    Logger.w(TAG, "MESSAGE_TOAST: dispositivo desligado")
//                    setChanged()
//                    updtNotifier.codigoErro = UpdateNotifier.Companion.ERRO_DE_COMUNICACAO
//                    notifyObservers(updtNotifier)
//                }
//            }
//        }
//    }
//
//    fun proximaOperacao(value: Int) {
//        idxOperacao = value
//        when (idxOperacao) {
//            REQUEST_START_COMM -> {
//                bluetoothService.cmd.startCommunication()
//                MeasuresActivity.ClearCrypt()
//            }
//            REQUEST_VERSION -> bluetoothService.cmd.getVersion() // 05
//            REQUEST_HOST_UPDT_ADDRESS -> {
//                tentativasUpgrade++
//                if (tentativasUpgrade <= 3) {
//                    indiceXml = 0
//                    // envia comando para abaixar da camada A3 para A2 (gravação de firmware)
//                    bluetoothService.cmd.setAddress(
//                        CommunicationProtocol.KWP_HOST_UPDT,
//                        CommunicationProtocol.KWP_TARGET_DIAG
//                    )
//                    bluetoothService.cmd.startCommunication() // 01
//                    MeasuresActivity.ClearCrypt()
//                } else {
//                    bluetoothService.cmd.setAddress(
//                        CommunicationProtocol.KWP_HOST_UPDT,
//                        CommunicationProtocol.KWP_TARGET_DIAG
//                    )
//                    bluetoothService.cmd.stopCommunication()
//
//                    // 14/05/2020 - diogo - correção paliativa:
//                    // quando o rasther não respondia ficava enviando stop communication
//                    // e podia dar erro e fechar o programa, pois tentava mostrar popup de
//                    // Atualização com erro mesmo tento já saído da tela de Atualização
//                    bluetoothService.pauseSending()
//
//                    // atualização dando erro
//                    // reiniciar equipamento
//                    // ...
//                    Logger.i(TAG, "Erro na atualização do firmware.")
//                    updtNotifier.codigoErro = UpdateNotifier.Companion.ERRO_DE_COMUNICACAO
//                    setChanged()
//                    notifyObservers(updtNotifier)
//                }
//            }
//            REQUEST_VERSION_UPDT -> bluetoothService.cmd.getVersion() // 05
//            REQUEST_SERIAL_NUMBER -> bluetoothService.cmd.serialNumberRead() // 21
//            REQUEST_SECURITY_1 -> bluetoothService.cmd.security() // 27
//            REQUEST_SECURITY_2 -> {
//                val key: ArrayList<String> = bluetoothService.cmd.generateKey(
//                    seed,
//                    serialNumber,
//                    CommunicationProtocol.SECURITY_UPGRADE
//                )
//                //Logger.w(TAG, "SECURITY 2: " + key.toString());
//                bluetoothService.write(key)
//                MeasuresActivity.SetCrypt(key)
//            }
//            REQUEST_UPGRADE -> {
//                Logger.w(
//                    TAG,
//                    "(" + indiceXml + ") " + firmwareXmlData[indiceXml].substring(0, 30) + " ..."
//                )
//                //if (indiceXml == 10) indiceXml = 1010;
//                bluetoothService.cmd.sendItemXmlFirmware(firmwareXmlData[indiceXml])
//            }
//            REQUEST_END_UPGRADE -> {
//                bluetoothService.cmd.endUpgrade()
//                var firmware =
//                    firmwareXml.replace(".", "").replace(",", "") // retira pontos e vírgulas
//                firmware = firmware.substring(0, 1) + "." + firmware.substring(1) // insere ponto
//                UpgradeActivity.versaoFirmware =
//                    firmware.replace(".", "").replace(",", "") // retira pontos e vírgulas;
//                sharedPreferences.edit()
//                    .putString(RastherDefaultActivity.PREFERENCES_FIRMWARE_VERSION, firmware)
//                    .commit()
//            }
//            REQUEST_STOP_COMM -> {
//                bluetoothService.cmd.stopCommunication()
//                // volta para endereço A3 para fazer diagnóstico
//                bluetoothService.cmd.setAddress(
//                    CommunicationProtocol.KWP_HOST_DIAG,
//                    CommunicationProtocol.KWP_TARGET_DIAG
//                )
//            }
//            REQUEST_TEST_A3_COMM -> {
//                try { // aguarda reiniciar rasther
//                    Thread.sleep(1000)
//                } catch (e: InterruptedException) {
//                    e.printStackTrace()
//                }
//                bluetoothService.cmd.startCommunication()
//                MeasuresActivity.ClearCrypt()
//            }
//            REQUEST_FINISH -> {
//                bluetoothService.stopCommunication()
//                updtNotifier.status = UpdateNotifier.Companion.STATUS_TERMINADO
//                setChanged()
//                notifyObservers(updtNotifier)
//            }
//        }
//    }
//
//    /**
//     * Devolve uma string com o nome do comando da máquina de estados.
//     * @param commandInt número que representa o comando.
//     * @return String com nome do comando.
//     */
//    private fun getNomeProximaOperacao(commandInt: Int): String {
//        var command = ""
//        command = when (commandInt) {
//            REQUEST_START_COMM -> "REQUEST_START_COMM"
//            REQUEST_VERSION -> "REQUEST_VERSION"
//            REQUEST_HOST_UPDT_ADDRESS -> "REQUEST_HOST_UPDT_ADDRESS"
//            REQUEST_VERSION_UPDT -> "REQUEST_VERSION_UPDT"
//            REQUEST_SERIAL_NUMBER -> "REQUEST_SERIAL_NUMBER"
//            REQUEST_SECURITY_1 -> "REQUEST_SECURITY_1"
//            REQUEST_SECURITY_2 -> "REQUEST_SECURITY_2"
//            REQUEST_UPGRADE -> "REQUEST_UPDATE"
//            REQUEST_END_UPGRADE -> "REQUEST_FINISH"
//            REQUEST_STOP_COMM -> "REQUEST_STOP_COMM"
//            REQUEST_TEST_A3_COMM -> "REQUEST_TEST_A3_COMM"
//            REQUEST_FINISH -> "REQUEST_FINISH"
//            else -> "Comando não definido! Atualizar getNomeProximaOperacao."
//        }
//        return command
//    }
//
//    companion object {
//        private const val TAG = "UpdateFirmware"
//
//        // controle da máquina de estados - idxOPeracao
//        private const val REQUEST_START_COMM = 0
//        private const val REQUEST_VERSION = 1
//        private const val REQUEST_HOST_UPDT_ADDRESS = 2
//        private const val REQUEST_VERSION_UPDT = 3
//        private const val REQUEST_SERIAL_NUMBER = 4
//        private const val REQUEST_SECURITY_1 = 5
//        private const val REQUEST_SECURITY_2 = 6
//        private const val REQUEST_UPGRADE = 7
//        private const val REQUEST_END_UPGRADE = 8
//        private const val REQUEST_STOP_COMM = 9
//        private const val REQUEST_TEST_A3_COMM = 10
//        private const val REQUEST_FINISH = 11
//    }
//
//    /**
//     * Construtor
//     */
//    init {
//        firmwareXmlData = ArrayList()
//        updtNotifier = UpdateNotifier(UpdateNotifier.Companion.TIPO_FIRMWARE)
//        firmwareXml = ""
//        setChanged() // observer
//        this.activity = activity
//        sharedPreferences =
//            activity.getSharedPreferences(RastherDefaultActivity.PREFERENCES, Context.MODE_PRIVATE)
//        this.bluetoothService = bluetoothService
//        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
//        idxOperacao = 0
//        firmwareXmlDataSize = 0
//        this.recuperaInterface = recuperaInterface
//    }
}