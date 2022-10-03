package br.com.tecnomotor.thanos.ui.home.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.config.ConfigApp
import br.com.tecnomotor.thanos.config.ConfigDevice
import br.com.tecnomotor.thanos.ui.dialog.InfoAppEquipamentosDialog
import br.com.tecnomotor.thanos.ui.dialog.RegistroProdutoDialog
import br.com.tecnomotor.thanos.ui.home.viewmodel.HomeViewModel
import br.com.tecnomotor.thanos.ui.menu.MenuActivity
import br.com.tecnomotor.thanos.ui.testesUnitarios.UnitTestActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

class HomeFragment : Fragment() {

    @OptIn(InternalCoroutinesApi::class, DelicateCoroutinesApi::class)
    private val viewModel: HomeViewModel by activityViewModels()

    private val controlador by lazy {
        findNavController()
    }

    private val configApp: ConfigApp = ConfigApp.getInstance()

    private val configDevice = ConfigDevice.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = getString(R.string.app_name)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configMenuButtons()
    }

    @OptIn(InternalCoroutinesApi::class, DelicateCoroutinesApi::class)
    private fun configMenuButtons() {

        btnNewWorkshop.setOnClickListener {
            val patch = HomeFragmentDirections.actionHomeFragmentToFragmentCarWorkshopInformation()
            controlador.navigate(patch)
        }

        btnDevRequest.setOnClickListener {
            val direcao =
                HomeFragmentDirections.actionHomeFragmentToSolicitacoesDeSistemasFragment()
            controlador.navigate(direcao)
        }
        btnReports.setOnClickListener {
            val direcao = HomeFragmentDirections.actionHomeFragmentToListaRelatoriosFragment()
            controlador.navigate(direcao)
        }
        btnDiagnostic.setOnClickListener {
            if (!configDevice.shouldIConnectToVCINow()) {
                val intent = Intent(requireContext(), MenuActivity::class.java)
                startActivity(intent)
            } else {
                if (configDevice.getDeviceMac().isNotEmpty()) {
                    fHome_Wait?.visibility = View.VISIBLE
                    btnDiagnostic?.isEnabled = false
                    viewModel.flagGoToMenu = true
                    configDevice.setPlatform(0).setVersion(0).setListMontadorasHabilitadas("")
                        .setIdMontadorasHabilitadas("")
                    viewModel.connect(configDevice.getDeviceMac())
                } else
                    controlador.navigate(HomeFragmentDirections.actionHomeFragmentToConnectDeviceFragment())
            }
        }
        logo_tecnomotor.setOnClickListener {
            Log.i(
                this.javaClass.simpleName,
                "Rasther: ${viewModel.isConnected()} - MAC: ${configDevice.getDeviceMac()}"
            )
            if (configDevice.getDeviceMac().isNotEmpty())
                if (!viewModel.isConnected())
                    viewModel.connect(configDevice.getDeviceMac())
                else
                    viewModel.disconnect()
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.menu_home_parear -> {
                val direcao = HomeFragmentDirections.actionHomeFragmentToConnectDeviceFragment()
                controlador.navigate(direcao)
            }
            R.id.menu_home_teste_unitario -> {
                val intent = Intent(requireContext(), UnitTestActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_home_register -> {
                RegistroProdutoDialog(requireContext()).mostra()
            }
            R.id.menu_home_info -> {
                InfoAppEquipamentosDialog(requireContext(),requireParentFragment(),configApp, LicencaDeUsoFragment() ).mostra()
            }
            R.id.menu_home_config -> {
                val direcao =
                    HomeFragmentDirections.actionHomeFragmentToConfiguracoesFragment()
                controlador.navigate(direcao)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}