package br.com.tecnomotor.thanos.service.update.toexclude

import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import br.com.tecnomotor.logger.Logger
import br.com.tecnomotor.thanos.service.soap.KserverRasther
import java.util.*

/**
 * Activity padrao. Todas as outras activities do app herdam desta.
 *
 * @author Caio Yassoyama
 */
open class RastherDefaultActivity : Activity(), Observer {
    val logger: Logger = Logger(showLog = true, appName = "Thanos")
    // utilizado para enviar e receber dados para o servidor por soap
    private val serverRasther: KserverRasther = KserverRasther()
    protected var sharedPreferences: SharedPreferences? = null
    protected var communicatingDialog: ProgressDialog? = null
    protected var alertDialog: AlertDialog? = null
    protected var alertAbout: AlertDialog? = null

    // True se esta recebendo ack
    //protected boolean isReceivingAck;
    private var loadBluetooth: Handler? = null
    private var countExecuteLoadBluetooth = 0

    /**
     * Flag do libStart - inicializa a tela com uma screen
     */
    protected var libStartScreens = false
    protected var activityContext: Context? = null
    protected var activityHandler: Handler? = null
    protected var batteryTension = 0

    // Extras passados pelo intent
    protected var extras: Bundle? = null
    protected var menu: Menu? = null
    val tag: String
        get() {
            var cName = intent.component!!.shortClassName
            while (cName.contains(".")) cName = cName.substring(cName.indexOf(".") + 1)
            return "$cName(Default)"
        }

    //    protected ServiceConnection serviceConnection = new ServiceConnection() {
    //        @Override
    //        public void onServiceConnected(ComponentName className, IBinder service) {
    //            try {
    //            	Logger.v(getTag(), "ServiceConnection() - onServiceConnected");
    //            	bluetoothService = BluetoothService.getInstance();
    //            	bluetoothService.setBoundBluetoothService(true);
    //            } catch (ClassCastException e) {
    //                e.printStackTrace();
    //            }
    //        }
    //
    //        @Override
    //        public void onServiceDisconnected(ComponentName arg0) {
    //        	bluetoothService.setBoundBluetoothService(false);
    //        }
    //    };
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRastherDefault()
    }

    protected fun onCreate(savedInstanceState: Bundle?, aHandler: Handler?) {
        super.onCreate(savedInstanceState)
        activityHandler = aHandler
        initRastherDefault()
    }

    private fun initRastherDefault() {
//        //Logger.i(getTag(), "initRastherDefault");
//        activityContext = this
//        if (backFromCrash == null) {
//            restartApp()
//        }
//        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
////        bindBluetoothService(this.getApplicationContext());
//
////        Logger.i(getTag(), "getSharedPreferences");
//        sharedPreferences = getSharedPreferences(
//            PREFERENCES,
//            MODE_PRIVATE
//        )
//        val languageToLoad = "PT" //sharedPreferences.getString(PREFERENCES_LANGUAGE, "PT")
//        val locale = Locale(languageToLoad)
//        Locale.setDefault(locale)
//        val config = Configuration()
//        config.locale = locale
//        baseContext.resources.updateConfiguration(
//            config,
//            baseContext.resources.displayMetrics
//        )
//        /**
//         * Rogério
//         * Carrega a instancia de Bluetooth service na classe RastherDefaultActivity
//         * para ser utilizada em todas as classes filhas
//         */
//        loadBluetooth = Handler()
//        val timeOutLoadBluetooth = AtomicLong(SystemClock.uptimeMillis() + 500L) //500ms
//        loadBluetooth!!.postAtTime(object : Runnable {
//            override fun run() {
//                logger.v(tag, "load bluetoothService instance")
//                if (countExecuteLoadBluetooth++ < 6 && BluetoothService.getInstance() == null) loadBluetooth!!.postAtTime(
//                    this,
//                    timeOutLoadBluetooth.addAndGet(500L)
//                ) else {
//                    bluetoothService = BluetoothService.getInstance()
//                    loadBluetooth!!.removeCallbacks(this)
//                    return
//                }
//                if (countExecuteLoadBluetooth >= 6) {
//                    logger.w(tag, "Timout load bluetoothService instance")
//                    loadBluetooth!!.removeCallbacks(this)
//                    return
//                }
//            }
//        }, timeOutLoadBluetooth.get())
    }

    override fun onResume() {
        super.onResume()
        //    	Logger.i(getTag(), "onResume");
    }

    override fun onStart() {
        // TODO Auto-generated method stub
        super.onStart()
        //    	Logger.i(getTag(), "onStart");
    }

    override fun onStop() {
        // TODO Auto-generated method stub
//    	Logger.i(getTag(), "onStop");
        super.onStop()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Captura o menu selecionado
        return when (item.itemId) {
            R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        //Logger.d("RastherDefaultActivity", "onBackPressed");
        //bluetoothService.pauseSending();
        super.onBackPressed()
    }

    private fun restartApp() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        //unbindBluetoothService();
    }

    //    protected void bindBluetoothService(Context context) {
    //    	Logger.v(getTag(), "start bindBluetoothService");
    //    	bluetoothService = BluetoothService.getInstance();
    //    	if ((bluetoothService == null) ||
    //    	  (!BluetoothService.getInstance().isBoundBluetoothService())) {
    //    		Logger.v(getTag(), "execute bindBluetoothService");
    //    		Intent intent = new Intent(context, BluetoothService.class);
    //    		bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    //    	} else {
    //    		Logger.v(getTag(), "getInstance BluetoothService");
    //    		bluetoothService = BluetoothService.getInstance();
    //    	}
    //    }
    //
    //    protected void unbindBluetoothService() {
    //    	Logger.v(getTag(), "start unbindBluetoothService");
    //        if (bluetoothService.isBoundBluetoothService()) {
    //        	Logger.v(getTag(), "execute unbindBluetoothService");
    //        	unbindService(serviceConnection);
    //            bluetoothService.setBoundBluetoothService(false);
    //        }
    //    }
    protected open fun showDialog() {
//        //Logger.d("RastherDefaultActivity", "showDialog()");
//        if (isDialogCommunicatingShowing) dismissDialog() else {
//            try {
//                communicatingDialog =
//                    Utils.showCommunicatingDialog(this, resources.getString(R.string.communicating))
//                communicatingDialog!!.setOnKeyListener { dialog, keyCode, event ->
//                    // This is the filter
//                    if (event.action == KeyEvent.ACTION_UP) {
//                        if (keyCode == KeyEvent.KEYCODE_SEARCH) {
//                            // Logger.d("RastherDefaultActivity",
//                            // "onKeyDown: KeyEvent.KEYCODE_SEARCH");
//                            true
//                        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
//                            //Logger.d("RastherDefaultActivity", "on key back pressed");
//                            showCancelAndRestartDialog()
//                            true
//                        } else {
//                            false
//                        }
//                    } else {
//                        false
//                    }
//                }
//            } catch (e: Exception) {
//                dismissDialog()
//                communicatingDialog = null
//            }
//        }
    }

    //Logger.e("RastherDefaultActivity", "Exception:: " + e.getMessage());
    protected val isDialogCommunicatingShowing: Boolean
        protected get() = try {
            communicatingDialog != null && communicatingDialog!!.isShowing
        } catch (e: Exception) {
            //Logger.e("RastherDefaultActivity", "Exception:: " + e.getMessage());
            dismissDialog()
            false
        }

    protected fun dismissDialog() {
//        Utils.dismissDialog(communicatingDialog)
//        try {
//            val dismissThread: Thread = object : Thread() {
//                override fun run() {
//                    while (isDialogCommunicatingShowing) {
//                        //Logger.d("RastherDefaultActivity", "Wait dismissDialog");
//                    }
//                }
//            }
//            dismissThread.start()
//        } catch (e: Exception) {
//            // TODO: handle exception
//        }
//        //Logger.w(TAG, "dismissDialog()");
    }

    protected fun showAlertDialog() {
        //Logger.d("RastherDefaultActivity", "showAlertDialog()");
        if (!this.isFinishing) {
            if (alertDialog != null) {
                if (!alertDialog!!.isShowing) {
                    alertDialog!!.show()
                }
            }
        }
    }

    protected fun showCancelAndRestartDialog() {

//        //Logger.d("RastherDefaultActivity", "showCancelAndRestartDialog()");
//        val onClickListenerPositive =
//            DialogInterface.OnClickListener { arg0, arg1 -> cancelAndRestart() }
//        val onClickListenerNegative =
//            DialogInterface.OnClickListener { arg0, arg1 -> dismissAlertDialog() }
//        alertDialog = Utils.showRetryPopUp(
//            resources.getString(R.string.warning), resources
//                .getString(R.string.cancel_and_restart), this,
//            onClickListenerPositive, onClickListenerNegative
//        )
//        alertDialog!!.setCancelable(false)
//        showAlertDialog()
    }

    protected fun cancelAndRestart() {
//        bluetoothService.pauseSending()
//        bluetoothService.stopCommunication()
//        dismissDialog()
//        //        Intent intent = new Intent(this, HomeActivity.class);
////        Bundle extras1 = new Bundle();
////        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        intent.putExtras(extras1);
////        startActivity(intent);
////        this.finish();
    }

    protected fun dismissAlertDialog() {
//        Utils.dismissAlertDialog(alertDialog)
//        val dismissThread: Thread = object : Thread() {
//            override fun run() {
//                while (alertDialog != null && alertDialog!!.isShowing) {
//                    //Logger.d("RastherDefaultActivity", "Wait dismissAlertDialog");
//                }
//            }
//        }
//        dismissThread.start()
    }

    protected fun returnHome() {
//        val intent = Intent(this, HomeActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//        startActivity(intent)
    }

    /**
     * Inicializa um screen
     * @param thisContext
     */
    protected fun startLibStartScreens(thisContext: Context?) {
//        logger.d(tag, "startLibStartScreens: startActivityForResult(ScreenLibStartActivity)")
//        libStartScreens = true
//        val intent = Intent(thisContext, ScreenLibStartActivity::class.java)
//        intent.putExtras(getIntent().extras!!)
//        startActivityForResult(intent, REQUEST_SCREEN)
    }

    protected fun exitApplication() {
//        val sairDiagnostico: AlertDialog
//        //Cria o gerador do AlertDialog
//        val builder = AlertDialog.Builder(this)
//        //define o titulo
//        //builder.setTitle("Informação");
//        //define a mensagem
//        builder.setMessage(R.string.finish_application)
//        //define um botão como positivo
//        builder.setPositiveButton(R.string.yes,
//            DialogInterface.OnClickListener { arg0, arg1 ->
//                val intent = Intent(applicationContext, HomeActivity::class.java)
//                intent.putExtra(EXTRA_EXIT_APP, true)
//                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//                startActivity(intent)
//            })
//        //define um botão como negativo.
//        builder.setNegativeButton(R.string.no,
//            DialogInterface.OnClickListener { arg0, arg1 -> })
//        //cria o AlertDialog
//        sairDiagnostico = builder.create()
//        Utils.centralizaAlertDialog(sairDiagnostico)
//        //Exibe
//        sairDiagnostico.show()
    }

    override fun startManagingCursor(c: Cursor) {
        // HONEYCOMB = 11

        // To solve the following error for honeycomb:
        // java.lang.RuntimeException: Unable to resume activity
        // java.lang.IllegalStateException: trying to requery an already closed
        // cursor
        if (Build.VERSION.SDK_INT < 11) {
            super.startManagingCursor(c)
        }
    }

    @SuppressLint("InflateParams")
    protected fun showPopUpAbout() {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle(resources.getString(R.string.about))
//        val factory = LayoutInflater.from(this)
//        val content: View = factory.inflate(R.layout.dialog_about, null)
//        val versionTxt = content.findViewById<View>(R.id.txt_version) as TextView
//        var versionName: String? = ""
//        versionName = try {
//            this.packageManager.getPackageInfo(this.packageName, 0).versionName
//        } catch (e: Exception) {
//            "-"
//        }
//        versionTxt.text = versionName
//        val serialNumberTxt = content
//            .findViewById<View>(R.id.txt_serial_number) as TextView
//        serialNumberTxt.text = sharedPreferences!!.getString(
//            PREFERENCES_SERIAL_NUMBER,  /*getResources().getString(R.string.unavailable_information)*/
//            "-"
//        )
//        val enabledVersionTxt = content
//            .findViewById<View>(R.id.txt_enabled_version) as TextView
//        enabledVersionTxt.text =
//            sharedPreferences!!.getString(PREFERENCES_VERSION, "-")
//        val firmwareVersionTxt = content.findViewById<View>(R.id.txt_firmware_version) as TextView
//        firmwareVersionTxt.text = sharedPreferences!!.getString(PREFERENCES_FIRMWARE_VERSION, "-")
//        val androidVersionTxt = content.findViewById<View>(R.id.txt_android_version) as TextView
//        androidVersionTxt.text = Build.VERSION.RELEASE // versão do android
//        val deviceNameTxt = content.findViewById<View>(R.id.txt_device_name) as TextView
//        deviceNameTxt.text = Build.MODEL // nome do dispositivo
//        val updateDateTxt = content.findViewById<View>(R.id.txt_update_date) as TextView
//        //updateDateTxt.setText(sharedPreferences.getString(PREFERENCES_UPDATE_DATE, ).split("\\s+")[0]); // última atualização
//        if (sharedPreferences!!.contains(PREFERENCES_UPDATE_DATE)) {
//            if (!sharedPreferences!!.getString(PREFERENCES_UPDATE_DATE, "")
//                    .split("\\s+").toTypedArray()[0].equals("01/01/2000", ignoreCase = true)
//            ) {
//                updateDateTxt.text = sharedPreferences!!.getString(
//                    PREFERENCES_UPDATE_DATE,
//                    ""
//                )!!.split("\\s+").toTypedArray()[0]
//            } else {
//                updateDateTxt.text = resources.getString(R.string.none_yet)
//            }
//        } else {
//            updateDateTxt.text = resources.getString(R.string.none_yet)
//        }
//        builder.setView(content).setPositiveButton(
//            resources.getString(R.string.ok)
//        ) { dialog, which -> }
//        alertAbout = builder.create()
//        Utils.centralizaAlertDialog(alertAbout)
//        alertAbout.show()
    }

    protected fun checkPermissions(permissions: Array<String?>): Boolean {
        for (permission in permissions) if (ContextCompat.checkSelfPermission(
                this,
                permission!!
            ) != PackageManager.PERMISSION_GRANTED
        ) return false
        return true
    }

    protected fun getPermissions(activity: Activity?, permissions: Array<String?>?, code: Int) {
        ActivityCompat.requestPermissions(activity!!, permissions!!, code)
    }

    protected fun startManualtec() {
//        val manualtec: Manualtec
//        manualtec = Manualtec(this)
//        val extra = intent.extras
//        var fileSchematic: String?
//        try {
//            fileSchematic = extra!!.getString(EXTRA_SCHEMATIC)
//            if (fileSchematic != null) {
//                fileSchematic = "$fileSchematic.edat"
//                val dir = getDir(RastherAndroid.DIR_SCHEMATICS, MODE_PRIVATE)
//                val edat = File(dir, fileSchematic)
//                if (edat.exists()) manualtec.open(fileSchematic) else showUpdateDialog()
//            } else {
//                alertDialog = Utils.showMessagePopUp(
//                    resources.getString(R.string.warning),
//                    resources.getString(R.string.not_schematic), this, null
//                )
//                alertDialog!!.setCancelable(false)
//                showAlertDialog()
//            }
//        } catch (e: Exception) {
//            alertDialog = Utils.showMessagePopUp(
//                resources.getString(R.string.warning),
//                resources.getString(R.string.not_schematic), this, null
//            )
//            alertDialog!!.setCancelable(false)
//            showAlertDialog()
//        }
    }

    private fun showUpdateDialog() {
//        val builder = AlertDialog.Builder(this)
//        builder.setMessage(R.string.pls_update)
//            .setPositiveButton(R.string.update_verb,
//                DialogInterface.OnClickListener { dialog, id ->
//                    if (bluetoothService.getConnectionState() !== BluetoothConnection.STATE_NONE) bluetoothService.stopCommunication()
//                    val intent = Intent(baseContext, SettingsActivity::class.java)
//                    intent.putExtra(
//                        EXTRA_SERIAL_NUMBER,
//                        getIntent().extras!!.getString(EXTRA_SERIAL_NUMBER)
//                    )
//                    intent.putExtra(
//                        EXTRA_VERSION,
//                        getIntent().extras!!.getString(EXTRA_VERSION)
//                    )
//                    intent.putExtra(
//                        EXTRA_PLATFORM,
//                        getIntent().extras!!.getString(EXTRA_PLATFORM)
//                    )
//                    intent.putExtra(EXTRA_EXIT_APP, false)
//                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                    startActivity(intent)
//                })
//            .setNegativeButton(R.string.cancel,
//                DialogInterface.OnClickListener { dialog, id ->
//                    //
//                })
//        val alert = builder.create()
//        Utils.centralizaAlertDialog(alert)
//        alert.show()
    }

    protected fun finishActivity() {
//        dismissAlertDialog()
//        dismissDialog()
//        finish()
//        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out)
    }

    protected fun showConnectionLost() {
//        val onClickListenerPositive =
//            DialogInterface.OnClickListener { arg0, arg1 -> //returnHome();
//                //finishActivity();
//                dismissDialog()
//            }
//        if (alertDialog != null) dismissAlertDialog()
//        //Logger.d("RastherDefault", "showConnectionLost");
//        try {
//            alertDialog = Utils.showErrorPopUp(
//                resources.getString(R.string.error_popup_title),
//                resources.getString(R.string.connection_device_lost),
//                this, onClickListenerPositive
//            )
//            alertDialog!!.show()
//        } catch (e: Exception) {
//            // TODO: handle exception
//            //returnHome();
//            //finishActivity();
//            dismissDialog()
//        }
    }

    protected fun startActivityForNewChoice() {
//        val intent: Intent
//        val extras1 = Bundle()
//        extras1.putString(EXTRA_VERSION, extras!!.getString(EXTRA_VERSION))
//        extras1.putString(EXTRA_PLATFORM, extras!!.getString(EXTRA_PLATFORM))
//        if (Integer.valueOf(extras!!.getString(EXTRA_PLATFORM)) != 4) {
//            intent =
//                if (extras!!.containsKey(EXTRA_FUNCTION_ID)) Intent(
//                    this,
//                    FunctionActivity::class.java
//                ) else Intent(this, AutomakerActivity::class.java)
//        } else {
//            intent = Intent(this, SystemActivity::class.java)
//            extras1.putString(EXTRA_AUTOMAKER_ID, extras!!.getString(EXTRA_AUTOMAKER_ID))
//            extras1.putString(EXTRA_AUTOMAKER_NAME, extras!!.getString(EXTRA_AUTOMAKER_NAME))
//            extras1.putString(
//                EXTRA_AUTOMAKER_IMG_PATH,
//                extras!!.getString(EXTRA_AUTOMAKER_IMG_PATH)
//            )
//            extras1.putBoolean(
//                EXTRA_IS_MOTORCYCLE_FLAG, extras!!.getBoolean(
//                    EXTRA_IS_MOTORCYCLE_FLAG
//                )
//            )
//            extras1.putString(EXTRA_VEHICLE_ID, extras!!.getString(EXTRA_VEHICLE_ID))
//            extras1.putString(EXTRA_VEHICLE_NAME, extras!!.getString(EXTRA_VEHICLE_NAME))
//            extras1.putBoolean(EXTRA_HAS_ENGINE_FLAG, extras!!.getBoolean(EXTRA_HAS_ENGINE_FLAG))
//            extras1.putString(EXTRA_ENGINE_ID, extras!!.getString(EXTRA_ENGINE_ID))
//            extras1.putString(EXTRA_ENGINE_NAME, extras!!.getString(EXTRA_ENGINE_NAME))
//            extras1.putString(EXTRA_SYSTEM_TYPE_ID, extras!!.getString(EXTRA_SYSTEM_TYPE_ID))
//            extras1.putString(EXTRA_SYSTEM_TYPE_NAME, extras!!.getString(EXTRA_SYSTEM_TYPE_NAME))
//            extras1.putString(
//                EXTRA_SYSTEM_TYPE_IMG_PATH, extras!!.getString(
//                    EXTRA_SYSTEM_TYPE_IMG_PATH
//                )
//            )
//        }
//        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//        intent.putExtras(extras1)
//        bluetoothService.pauseSending()
//        bluetoothService.setSearchApplication(false)
//        startActivity(intent)
//        finishActivity()
    }

    protected fun startInitialActivity(
        startActivity: Context?,
        version: Int, platform: Int, serialNumber: String/*, diagType: DiagType*/
    ) {
//        var version = version
//        var platform = platform
//        var serialNumber = serialNumber
//        logger.i(tag, "startInitialActivity")
//        if (diagType === DiagType.JUST_CONNECT) {
//            //Logger.d("RastherDefaultActivity", "DiagType.JUST_CONECT");
//            dismissDialog()
//            return
//        }
//        sharedPreferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE)
//        if (version <= 0) version =
//            Integer.valueOf(sharedPreferences.getString(PREFERENCES_VERSION, "0"))
//        if (platform <= 0) platform = Integer.valueOf(
//            sharedPreferences.getString(
//                PREFERENCES_PLATFORM, "1"
//            )
//        )
//        if (serialNumber == "0" || serialNumber == "" || serialNumber == resources.getString(R.string.unavailable_information)) serialNumber =
//            sharedPreferences.getString(
//                PREFERENCES_SERIAL_NUMBER,
//                resources.getString(R.string.unavailable_information)
//            )!!
//        dismissDialog()
//        val extras = Bundle()
//        extras.putString(EXTRA_VERSION, version.toString())
//        extras.putString(EXTRA_PLATFORM, platform.toString())
//        extras.putString(EXTRA_SERIAL_NUMBER, serialNumber)
//        sharedPreferences.edit()
//            .putString(PREFERENCES_VERSION, version.toString()).commit()
//        sharedPreferences.edit()
//            .putString(PREFERENCES_PLATFORM, platform.toString()).commit()
//        sharedPreferences.edit()
//            .putString(
//                PREFERENCES_SERIAL_NUMBER,
//                serialNumber
//            ).commit()
//
//
//        // obtém informação de versão e firmware pelo webservice
//        // se estiver diferente do servidor e local, envia para servidor
//        // só executa consulta se possuir número de série
//        val md5SerialNumber: String
//        try {
//            if (sharedPreferences.contains(PREFERENCES_PLATFORM)) {
//                md5SerialNumber = Md5Helper.getMD5EncryptedString(serialNumber)
//                serverRasther.addObserver(this)
//                serverRasther.getEquipamento(serialNumber, md5SerialNumber)
//                serverRasther.getCliente(serialNumber, md5SerialNumber)
//            }
//        } catch (e: Exception) {
//            logger.e(tag, "Erro ao tentar executar taskUpdateEquip: " + e.message)
//        }
//        val fwVersion = java.lang.Float.valueOf(
//            sharedPreferences.getString(
//                PREFERENCES_FIRMWARE_VERSION, "0"
//            )
//        )
//        val boolPrefUpdAtec472 =
//            sharedPreferences.getBoolean(PREFERENCES_UPDATE_ATEC_NOV_2019, false)
//        //Logger.i("RastherDefaultActivity", "PREFERENCES_UPDATE_ATEC_NOV_2019: " + boolPrefUpdAtec472);
//        // verifica se é a primeira execução ou se ainda não atualizou atecs com nova senha ou se firmware é antigo
//        // código também escrito diretamente em HomeActivity
//        if (sharedPreferences.getBoolean(
//                PREFERENCES_FIRST_RUN,
//                true
//            ) || boolPrefUpdAtec472 == false
//        ) {
//            val intent = Intent(this, InstructionsUpgradeActivity::class.java)
//            intent.putExtra(EXTRA_MSG_BOAS_VINDAS_DIAG, EXTRA_MSG_BOAS_VINDAS_DIAG)
//            startActivityForResult(intent, REQUEST_UPDATE)
//            return
//        } else if (fwVersion < Utils.FW_VERSION_NOV_2019) {
//            val intent = Intent(this, UpgradeActivity::class.java)
//            intent.putExtra(EXTRA_RECOVER_FIRMWARE, EXTRA_RECOVER_FIRMWARE)
//            startActivityForResult(intent, REQUEST_UPDATE)
//            return
//        }
//        var intent: Intent? = null
//        if (diagType === DiagType.DIAG || diagType === DiagType.FUNCTION) {
//            if (platform != 4 && diagType === DiagType.DIAG) intent = Intent(
//                startActivity,
//                AutomakerActivity::class.java
//            ) else if (diagType === DiagType.FUNCTION) {
//                if (sharedPreferences.getBoolean(PREFERENCES_HAS_FUNCIONS_MAKER, false)) intent =
//                    Intent(
//                        startActivity,
//                        FunctionActivity::class.java
//                    ) else Toast.makeText(
//                    this,
//                    getString(R.string.doesNotSuport),
//                    Toast.LENGTH_LONG
//                ).show()
//            } else {
//                intent = Intent(startActivity, SystemActivity::class.java)
//                extras.putString(EXTRA_AUTOMAKER_ID, "24")
//                extras.putString(EXTRA_AUTOMAKER_NAME, "")
//                extras.putString(EXTRA_AUTOMAKER_IMG_PATH, "images/automakers/dot.png")
//                extras.putBoolean(EXTRA_IS_MOTORCYCLE_FLAG, false)
//                extras.putString(EXTRA_VEHICLE_ID, "362")
//                extras.putString(EXTRA_VEHICLE_NAME, "")
//                extras.putBoolean(EXTRA_HAS_ENGINE_FLAG, false)
//                extras.putString(EXTRA_ENGINE_ID, "")
//                extras.putString(EXTRA_ENGINE_NAME, "")
//                extras.putString(EXTRA_SYSTEM_TYPE_ID, "17")
//                extras.putString(EXTRA_SYSTEM_TYPE_NAME, "")
//                extras.putString(EXTRA_SYSTEM_TYPE_IMG_PATH, "images/systemtypes/1.rsyt")
//            }
//        } else if (diagType === DiagType.UNDER) {
//            if (sharedPreferences.getInt(PREFERENCES_BOOTID, 0) == 15) intent = Intent(
//                startActivity,
//                UnderSelectTestActivity::class.java
//            ) else Toast.makeText(this, getString(R.string.doesNotSuport), Toast.LENGTH_LONG).show()
//        }
//        bluetoothService.pauseSending()
//        bluetoothService.setSearchApplication(false)
//        if (intent == null) return
//        intent.putExtras(extras)
//        startActivity(intent)
    }

    protected fun verifyBattery(tension: Int) {
//        if (tension == 0) return
//        batteryTension = tension
//        runOnUiThread(Runnable {
//            if (menu == null) return@Runnable
//            menu!!.findItem(R.id.battery).isVisible = true
//            menu!!.findItem(R.id.battery).setIcon(
//                Utils.getBatteryImageByTension(
//                    this@RastherDefaultActivity,
//                    batteryTension
//                )
//            )
//        })
    }

    /**
     * Mostra uma mensagem de erro, deleta o módulo e solicita para baixar novamente
     * @param fileName
     */
    protected fun handleError(fileName: String) {
//        if (isDialogCommunicatingShowing) dismissDialog()
//        logger.d(tag, "HandleError($fileName)")
//        try {
//            Utils.deleteFileFromInternalStorage(this.baseContext, fileName)
//        } catch (e: Exception) {
//        }
//
//        // remove a hora da data para forçar a fazer a atualização novamente
//        //SharedPreferences sharedPreferences = context.getSharedPreferences(RastherDefaultActivity.PREFERENCES, Context.MODE_PRIVATE);
//        val dateLast = sharedPreferences!!.getString(
//            PREFERENCES_UPDATE_DATE, ""
//        )!!.split("\\s+").toTypedArray()[0]
//        sharedPreferences!!.edit().putString(PREFERENCES_UPDATE_DATE, dateLast).commit()
//
//        // updateAtec
//        val intent = Intent(this, UpgradeActivity::class.java)
//        intent.putExtra(EXTRA_UPDATE_JUST_SW, EXTRA_UPDATE_JUST_SW)
//        startActivityForResult(intent, REQUEST_UPDATE)
    }

    override fun update(observable: Observable, data: Any) {
//        // TODO Auto-generated method stub
//        if (observable.javaClass == KserverRasther::class.java) {
//            if (data.javaClass == Equipamento::class.java) {
////    			Equipamento equipamento = (Equipamento) data;
////    			String md5SerialNumber = Md5Helper.getMD5EncryptedString(equipamento.getNumSerie());
////    			equipamento.setKeySecurity(md5SerialNumber);
////    			equipamento.setFirmware(sharedPreferences.getString(PREFERENCES_FIRMWARE_VERSION, "0"));
////    			equipamento.setVersao(version);
////		    	serverRasther.setEquipamento(serverRasther.getEquipamento());
//            }
//        }
    }

    companion object {
        // Tag para debug
        const val DEBUG_TAG = "Rasther Log"

        // Preferencias do app, usadas para salvar dados
        const val PREFERENCES = "Rasther Preferences"
        const val PREFERENCES_DATABASE_VERSION = "Database Version"
        const val PREFERENCES_NAME = "Name"
        const val PREFERENCES_ADDRESS = "Address"
        const val PREFERENCES_PHONE = "Phone"
        const val PREFERENCES_EMAIL = "Email"
        const val PREFERENCES_LOGO = "Logo"
        const val PREFERENCES_LOGO_FLAG = "Logo Flag"
        const val PREFERENCES_BLUETOOTH = "Bluetooth"
        const val PREFERENCES_LANGUAGE = "Language"
        const val PREFERENCES_LANGUAGE_POS = "Language Position"
        const val PREFERENCES_SCREENSHOT_COUNT = "Print Count"
        const val PREFERENCES_ECU_FF_ID = "ECU FF ID"
        const val PREFERENCES_ECU_FF_NAME = "ECU FF NAME"
        const val PREFERENCES_VERSION = "Version"
        const val PREFERENCES_PLATFORM = "Platform"
        const val PREFERENCES_SERIAL_NUMBER = "Serial Number"
        const val PREFERENCES_UPDATE_DATE = "Update Date"
        const val PREFERENCES_DEMONSTRATION_MODE = "Demonstration Mode"
        const val PREFERENCES_BOOTID = "BootId"
        const val PREFERENCES_NOME_EQUIPAMENTO = "NomeEquipamento"
        const val PREFERENCES_FIRMWARE_VERSION = "FirmwareVersion"
        const val PREFERENCES_UPDATE_ATEC_NOV_2019 = "UpdateAtecNov2019"
        const val PREFERENCES_HAS_FUNCIONS_MAKER = "functions_maker"
        const val PREFERENCES_HAS_SHOCK_MAKER = "shock_maker"
        const val PREFERENCES_ENABLED_MAKERS = "enabled makers"
        const val PREFERENCES_FIRST_RUN =
            "FIRST_TIME" // first time execution of app after clean installation
        const val PREFERENCES_FIRST_RUN_DISCONNECTED =
            "First execution - have to save automakers in sharedpreferences"
        const val PREFERENCES_BT_CONNECTION_DATE = "Bluetooth connection date"
        const val PREFERENCES_SCRITP_STATE = "isStartingScript"
        const val PREFERENCES_PROTOCOL_STARTED = "protocolStarted"
        const val PREFERENCES_USER_LICENSE_CHECKED = "Usuário aceitou licença de uso"
        const val PREFERENCES_CONNECT = "Conectar no dispositivo para pegar numero de serie"
        const val PREFERENCES_COPY_COUNTRY_FILE =
            "Copiou arquivo country.xml para armazenamento interno"

        // Intent request codes
        protected const val REQUEST_INIT_CONNECTION = 0
        protected const val REQUEST_CONNECT_DEVICE = 1
        protected const val REQUEST_ENABLE_BT = 2
        protected const val REQUEST_IMAGE_SELECTION = 3
        protected const val REQUEST_UPDATE = 4
        protected const val REQUEST_DIAGNOSTIC = 5
        protected const val REQUEST_SCREEN = 6
        protected const val REQUEST_SCREEN_INFO = 7
        protected const val REQUEST_USER_LICENSE = 8
        protected const val REQUEST_PRODUCT_REGISTER = 9
        protected const val REQUEST_SETTINGS_ACTIVITY = 10

        // Utils
        const val PREFERENCES_CONNECTION_FLAG = "Connection Flag"

        // Extras para serem passados nos intents
        const val EXTRA_EXIT_APP = "Exit Apllication"
        const val EXTRA_SERIAL_NUMBER = "Serial Number"
        const val EXTRA_VERSION = "Version"
        const val EXTRA_PLATFORM = "Platform"
        const val EXTRA_AUTOMAKER_ID = "Automaker ID"
        const val EXTRA_AUTOMAKER_NAME = "Automaker Name"
        const val EXTRA_AUTOMAKER_IMG_PATH = "Automaker Image Path"
        const val EXTRA_IS_MOTORCYCLE_FLAG = "Motorcycle Flag"
        const val EXTRA_VEHICLE_ID = "Vehicle ID"
        const val EXTRA_VEHICLE_NAME = "Vehicle Name"
        const val EXTRA_HAS_ENGINE_FLAG = "Engine Flag"
        const val EXTRA_ENGINE_ID = "Engine ID"
        const val EXTRA_ENGINE_NAME = "Engine Name"
        const val EXTRA_SYSTEM_TYPE_FLAG = "System Type FLAG"
        const val EXTRA_SYSTEM_TYPE_ID = "System Type ID"
        const val EXTRA_SYSTEM_TYPE_NAME = "System Type Name"
        const val EXTRA_SYSTEM_TYPE_IMG_PATH = "System Type Image Path"
        const val EXTRA_SYSTEM_ID = "System ID"
        const val EXTRA_SYSTEM_NAME = "System Name"
        const val EXTRA_CONNECTOR_ID = "Conector ID"
        const val EXTRA_CONNECTOR_NAME = "Conector Name"
        const val EXTRA_CONNECTOR_POS = "Conector Position"
        const val EXTRA_MODULE_XML = "Module XML"
        const val EXTRA_DTC_GLOBAL = "Global DTC"
        const val EXTRA_DTC_OBDII = "OBDII DTC"
        const val EXTRA_APLID = "Application id"
        const val EXTRA_APLIDS = "AplIds"
        const val EXTRA_IS_D0 = "Is Connector D0"
        const val EXTRA_SCHEMATIC = "Schematic"
        const val EXTRA_FUNCTION_ID = "Function ID"
        const val EXTRA_ALL_FUNCTION_ID = "ALL Function ID"
        const val EXTRA_FUNCTION_NAME = "Function Name"
        const val EXTRA_MUDOU_DE_MODULO = "Mudou de Modulo"
        const val EXTRA_MSG_BOAS_VINDAS_DIAG = "Mensagem de boas vindas no update do Diag"
        const val EXTRA_RECOVER_FIRMWARE = "recuperar interface lógica"
        const val EXTRA_UPDATE_JUST_SW = "atualizar apenas software"
        const val EXTRA_ERRO_REGISTRO_PRODUTO = "Código de erro do registro de produto"
        const val EXTRA_SEARCH_APPLICATION = "Search Application"
        const val EXTRA_CLEAR_TASK = "Clear task"
        const val EXTRA_AUTO_LOAD_NEXT_ACTIVITY = "AutoLoadNextActivity"

        // Extras dos pinos
        const val EXTRA_PIN_X = "Connector Pin X"
        const val EXTRA_PIN_Y = "Connector Pin Y"

        // Extras dos graficos
        const val EXTRA_N_OF_GRAPHS = "Number of Graphs"
        const val EXTRA_GRAPH_1 = "Graph 1"
        const val EXTRA_GRAPH_2 = "Graph 2"

        // Extra opcao de analise grafica
        const val EXTRA_OPTION_GRAPH_ANALISYS = "Option Graphic Analysis"
        const val EXTRA_SCREEN_ITEM = "Screen Item Position"
        const val EXTRA_SCREEN_ACTION = "Screen Chosen Action"
        const val EXTRA_IS_MODULE_2000 = "Is Module 2000"
        protected const val MAX_TIME_DESCONNECTED =
            (7 * 24 * 60 * 60 * 1000 // 7 dias em milissegundos
                    ).toLong()
        const val HAS_MEASURE = "has measure"
        const val BATTERY_EXTRA = "BATTERY_EXTRA"
//        protected var bluetoothService: BluetoothService? = null
        protected var backFromCrash: String? = null
    }
}