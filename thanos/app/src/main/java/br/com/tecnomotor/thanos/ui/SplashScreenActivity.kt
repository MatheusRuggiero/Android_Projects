package br.com.tecnomotor.thanos.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.tecnomotor.rasther.RastherRepository
import br.com.tecnomotor.rasther.utils.FilesUtils
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.config.ConfigApp
import br.com.tecnomotor.thanos.config.ConfigDevice
import br.com.tecnomotor.thanos.database.cliente.ClienteDatabase
import br.com.tecnomotor.thanos.database.menu.MenuDatabase
import br.com.tecnomotor.thanos.database.messages.MessagesDatabase
import br.com.tecnomotor.thanos.model.Selecao
import br.com.tecnomotor.thanos.repository.menu.MenuRepository
import br.com.tecnomotor.thanos.ui.home.HomeActivity
import kotlinx.coroutines.*

@SuppressLint("CustomSplashScreen")
@InternalCoroutinesApi
@DelicateCoroutinesApi
class SplashScreenActivity : AppCompatActivity() {

    private val timeStart = System.currentTimeMillis()
    private val delayNextActivity = 4000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()
        copiaArquivosArmazenamentoInterno() // TODO remover depois
        loading()
    }

    private fun copiaArquivosArmazenamentoInterno() {
        FilesUtils(this).copyConfigToInternal()
        FilesUtils(this).copyImagesToInternal()
        FilesUtils(this).copyPositionsToInternal()
        FilesUtils(this).copyAtecsToInternal()
        FilesUtils(this).copyHtmlToInternal()
    }

    @Synchronized
    private fun loading() {
        GlobalScope.launch(Dispatchers.IO) {
            //delay(4000)
            // executa apenas na primeira execução -------------------------------------------------
            val configApp = ConfigApp.getInstance(applicationContext)
            if (configApp.firstExecution()) {
                copiaArquivosArmazenamentoInterno()
                configApp.setFirstExecution(false)
            }
            //ConfigDevice.getInstance(applicationContext)

            val configDevice = ConfigDevice.getInstance(applicationContext)
            configDevice
                .setDeviceMac("7C:87:CE:4E:41:86")
                .setConnectionDate()
                .setVersion(22)
                .setPlatform(1)
                .setSerialNumber("133615")
                .setBootId(20)
                .setFirmwareVersion("3.100")
                .setFirstConnectionMade(true)
            /** ----- */

            //Inicializando banco de dados
            MenuDatabase.getInstance(applicationContext)
            MessagesDatabase.getInstance(applicationContext)
            ClienteDatabase.getInstance(applicationContext)

            //Aqui inicializará a instancia de seleção
            Selecao.getInstance()

            MenuRepository.getInstance()
            //--------------------------------------------------------------------------------------

            if ((System.currentTimeMillis() - timeStart) < delayNextActivity) {
                delay(delayNextActivity - (System.currentTimeMillis() - timeStart))
            }
            nextActivity()
        }
    }

    private fun nextActivity(){
        val rastherRepository = RastherRepository.getInstance(this.applicationContext)
        rastherRepository.numberOfConnectionAttempts = ConfigDevice.getInstance().getNumberOfConnectionAttempts()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}