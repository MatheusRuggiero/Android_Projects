package br.com.tecnomotor.thanos.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import br.com.tecnomotor.rasther.DevicesConnection
import br.com.tecnomotor.rasther.RastherInfo
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.config.ConfigApp
import br.com.tecnomotor.thanos.config.ConfigDevice
import br.com.tecnomotor.thanos.database.menu.MenuDatabase
import br.com.tecnomotor.thanos.model.Selecao
import br.com.tecnomotor.thanos.model.menu.Montadora
import br.com.tecnomotor.thanos.model.menu.Plataforma
import br.com.tecnomotor.thanos.model.menu.Versao
import br.com.tecnomotor.thanos.model.menu.toMontadora
import br.com.tecnomotor.thanos.ui.home.fragments.HomeFragmentDirections
import br.com.tecnomotor.thanos.ui.home.fragments.PermissoesFragment
import br.com.tecnomotor.thanos.ui.home.viewmodel.HomeViewModel
import br.com.tecnomotor.thanos.ui.menu.MenuActivity
import br.com.tecnomotor.thanos.util.CheckPermission
import br.com.tecnomotor.thanos.util.ConvertClass
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@DelicateCoroutinesApi
class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels()

    private var isInitialization = false
    private val configDevice = ConfigDevice.getInstance()

    private val controlador by lazy {
        findNavController(R.id.home_activity_nav_host)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.custom_back_arrow_ripple)

        loadConfig()

        //Monitora a alterações de fragment
        controlador.addOnDestinationChangedListener {
                controller, destination, arguments ->
            supportActionBar?.setDisplayHomeAsUpEnabled(destination.id !in
                    arrayOf(R.id.homeFragment, R.id.conexaoFragment, R.id.permissoesFragment))
            if (destination.id in arrayOf(R.id.conexaoFragment, R.id.permissoesFragment,
                    R.id.licencaDeUsoFragment)) {
                supportActionBar?.hide()
            } else {
                supportActionBar?.show()
            }
            //Cancelando o discovery sempre que sai de um fragment
            viewModel.cancelDiscovery()
        }

        //Verificando as permissões
        if (!CheckPermission.permissionsGranted(this, PermissoesFragment.permissoes)) {
            controlador.navigate(HomeFragmentDirections.actionHomeFragmentToPermissoesFragment())
        }
        else if (!ConfigApp.getInstance().getLicenseToUse()) {
            controlador.navigate(HomeFragmentDirections.actionHomeFragmentToLicencaDeUsoFragment())
        }
        //Indo para inicialização do hardware (primeiro uso)
        else if (configDevice.getDeviceMac().isEmpty()) {
            controlador.navigate(HomeFragmentDirections.actionHomeFragmentToConexaoFragment())
        }
    }

    override fun onResume() {
        super.onResume()
        if (CheckPermission.permissionsGranted(this, PermissoesFragment.permissoes)) {
            viewModel.reCreateRepository(this.applicationContext)
            viewModel.statusConnectionLiveData.observe(this) {
                Log.i(this.javaClass.simpleName, it.toString())
                when (it.devicesStatus) {
                    DevicesConnection.DevicesStatus.CONNECTED -> {
                        try {
                            Log.w(
                                this.javaClass.simpleName,
                                viewModel.rasther().getDescription().toString()
                            )
                            ConfigDevice.getInstance()
                                .setDeviceMac(viewModel.rasther().getDescription().address)
                                .setConnectionDate()
                                .setFirstConnectionMade(true)
                            viewModel.initialization(false)
                            isInitialization = true
                            if (isInitialization) {
                                viewModel.rasther().rastherInfoLiveData.observe(this) { rastherInfo ->
                                    Log.i(this.javaClass.simpleName, rastherInfo.toString())

                                    setRastherInfo(rastherInfo)
                                }
                            }
                        } catch (e: Exception) {
                            Log.e(this.javaClass.simpleName, "Erro: $e")
                        }
                    }
                    DevicesConnection.DevicesStatus.CONNECTING -> {}
                    else -> {
                        fHome_Wait?.visibility = View.INVISIBLE
                        btnDiagnostic?.isEnabled = true
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.statusConnectionLiveData.removeObservers(this)
    }

    override fun onDestroy() {
        if (viewModel.isConnected()) viewModel.disconnect()
        super.onDestroy()
    }

    private fun loadConfig() {
        setPlataforma(configDevice.getPlatform())
        setVersao(configDevice.getVersion())
    }

    private fun setRastherInfo(rastherInfo: RastherInfo) {
        configDevice.apply {
            this.setPlatform(rastherInfo.plataforma)
                .setVersion(rastherInfo.versao)
                .setBootId(rastherInfo.bootId)
                .setFirmwareVersion(rastherInfo.versaoFirmware)
                .setIdMontadorasHabilitadas(rastherInfo.montadoras.joinToString())
                .setSerialNumber(rastherInfo.numSerie)

            setPlataforma(rastherInfo.plataforma)
            setVersao(rastherInfo.versao)
            setMontadoras(rastherInfo.montadoras)
        }
    }

    private fun setVersao(verId: Int) {
        MenuDatabase.getInstance().let { db ->
            db.versaoDao().getById(verId.toLong()).observe(this) { versaoEntity ->
                if (versaoEntity != null) {
                    val versao = Versao(versaoEntity.id)
                    Selecao.getInstance().setVersao(versao)
                } else {
                    //Versão não encontrada
                    Log.e(this.javaClass.simpleName, "Versão não disponível")
                    //Debug
                    Selecao.getInstance().setVersao(Versao(14))
                }
            }
        }
    }

    private fun setPlataforma(plaId: Int) {
        MenuDatabase.getInstance().let { db ->
            db.plataformaDao().getById(plaId.toLong()).observe(this) { plataformaEntity ->
                if (plataformaEntity != null) {
                    val plataforma = Plataforma(plataformaEntity.id, plataformaEntity.nome, plataformaEntity.versaoHabilitada)
                    Selecao.getInstance().setPlataforma(plataforma)
                } else {
                    //Plataforma não encontrada
                    Log.e(this.javaClass.simpleName, "Plataforma não disponível")
                    //Debug
                    Selecao.getInstance().setPlataforma(Plataforma(1, "S", 18))
                }
            }
        }
    }

    private fun setMontadoras(monIds: ArrayList<Int>) {
        MenuDatabase.getInstance().let { db ->
            db.montadoraDao().getById(monIds).observe(this) { montadoras ->
                if (montadoras != null) {
                    val listMontadora: ArrayList<Montadora> = arrayListOf()
                    montadoras.forEach { listMontadora.add(it.toMontadora()) }
                    configDevice.setListMontadorasHabilitadas(ConvertClass.classToJson(listMontadora.toArray()))

                    if (viewModel.flagGoToMenu) {
                        fHome_Wait?.visibility = View.INVISIBLE
                        viewModel.flagGoToMenu = false
                        btnDiagnostic.isEnabled = true
                        val intent = Intent(this, MenuActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                    Log.e(this.javaClass.simpleName, "Montadoras não encontradas")
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}