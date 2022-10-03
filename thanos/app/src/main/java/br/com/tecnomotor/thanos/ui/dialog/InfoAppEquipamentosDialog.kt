package br.com.tecnomotor.thanos.ui.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import br.com.tecnomotor.thanos.BuildConfig
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.adapter.dialog.InfoAppEquipamentosAdapter
import br.com.tecnomotor.thanos.config.ConfigApp
import br.com.tecnomotor.thanos.config.ConfigDevice
import br.com.tecnomotor.thanos.model.menu.Montadora
import br.com.tecnomotor.thanos.ui.home.fragments.HomeFragmentDirections
import br.com.tecnomotor.thanos.ui.home.fragments.LicencaDeUsoFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.licenca_de_uso.*
import java.text.SimpleDateFormat
import java.util.*


class InfoAppEquipamentosDialog(
    private val context: Context,
    fragment: Fragment,
    private val configApp: ConfigApp,
    private val licencaDeUsoFragment: LicencaDeUsoFragment,
) {
    private val controlador by lazy {
        NavHostFragment.findNavController(fragment)
    }
    @SuppressLint("InflateParams")
    private lateinit var viewInfo: View
    private lateinit var versaoApp: TextView
    private lateinit var versaoHabilitada: TextView
    private lateinit var ultimaAtt: TextView
    private lateinit var dataVencimento: TextView
    private lateinit var firmware: TextView
    private lateinit var nSerie: TextView
    private lateinit var vAndroid: TextView
    private lateinit var modeloAndroid: TextView
    private lateinit var btnLicencaUso: Button
    private lateinit var rvListMontadoras: RecyclerView
    private lateinit var adapter: InfoAppEquipamentosAdapter
    private lateinit var configDevice: ConfigDevice
    val TAG = "InfoAppDialog"

    fun mostra() {
        inicializaLayoutView()
        inicializaViews()
        configuraTextViews()
        configuraDialog()
//        getMontadoras().sort()
        configuraAdapter(getMontadoras().toMutableList())
        configBtnLicenca()
    }

    private fun getMontadoras(): Array<Montadora> {
        val montadorasHabilitadas = configDevice.getListMontadorasHabilitadas()
        return montadorasHabilitadas
    }

    private fun inicializaViews() {
        versaoApp = viewInfo.findViewById(R.id.info_app_equipamento_app_versao)
        versaoHabilitada = viewInfo.findViewById(R.id.info_app_equipamento_v_habilitada_versao)
        ultimaAtt = viewInfo.findViewById(R.id.info_app_equipamento_ultima_att_data)
        dataVencimento = viewInfo.findViewById(R.id.info_app_equipamento_vencimento_data)
        firmware = viewInfo.findViewById(R.id.info_app_equipamento_firmware_versao)
        nSerie = viewInfo.findViewById(R.id.info_app_equipamento_n_serie_versao)
        vAndroid = viewInfo.findViewById(R.id.info_app_equipamento_android_versao)
        modeloAndroid = viewInfo.findViewById(R.id.info_app_equipamento_android_modelo_versao)
        rvListMontadoras = viewInfo.findViewById(R.id.info_app_equipamento_recyclerView)
        btnLicencaUso = viewInfo.findViewById(R.id.info_app_equipamento_btn_licenca)
    }

    private fun configBtnLicenca() {
            val concordo = licencaDeUsoFragment.licenca_de_uso_checkboxbtn_concordo

        btnLicencaUso.setOnClickListener {
            val concorda = configApp.getLicenseToUse()
            if (concorda){
                try {
            controlador.navigate(HomeFragmentDirections.actionHomeFragmentToLicencaDeUsoFragment())
                concordo.isChecked
                !concordo.isEnabled

                }catch(e: Exception){
                    Log.i("licenca", "erro")
                }
            }

        }

    }

    private fun inicializaLayoutView() {
        viewInfo =
            LayoutInflater.from(context).inflate(R.layout.dialog_info_app_equipamento, null)
    }

    private fun configuraDialog() {
        MaterialAlertDialogBuilder(context)
            .setView(viewInfo)
            .setNegativeButton("Fechar") { _, _ -> }
            .show()
    }

    @SuppressLint("SetTextI18n")
    private fun configuraTextViews() {
        configDevice = ConfigDevice.getInstance(context)
        val lastConnectedDate = SimpleDateFormat("dd/MM/yyyy HH:mm").format(Date())
//        val lastConnectedDate = SimpleDateFormat("dd/MM/yyyy HH:mm")
//            .format(configDevice.getDeviceLastConnectedDate())
        val platform = configDevice.getPlatform().toString()
        val versao = configDevice.getVersion().toString()

        // Informações da VCI
        versaoHabilitada.text = platform + versao
        ultimaAtt.text = lastConnectedDate
        nSerie.text = configDevice.getSerialNumber()
        firmware.text = configDevice.getFirmwareVersion()
//        dataVencimento.text = configDevice

        // Informações do App
        versaoApp.text = BuildConfig.VERSION_NAME

        // Informações do Tablet
        vAndroid.text = Build.VERSION.RELEASE
        modeloAndroid.text = Build.MODEL
    }

    private fun configuraAdapter(montadoras: MutableList<Montadora>) {
        adapter = InfoAppEquipamentosAdapter(context, montadoras)
        rvListMontadoras.adapter = adapter
    }
}


