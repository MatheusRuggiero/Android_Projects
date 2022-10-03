package br.com.tecnomotor.thanos.ui.home.fragments

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import br.com.tecnomotor.bluetoothserial.DiscoveryStatus
import br.com.tecnomotor.rasther.DevicesConnection
import br.com.tecnomotor.rasther.RastherList
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.adapter.ConnectDeviceAdapter
import br.com.tecnomotor.thanos.ui.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_connect_device.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

class ConnectDeviceFragment : Fragment() {

    private val REQUEST_ENABLE_BT = 101

    @OptIn(InternalCoroutinesApi::class, DelicateCoroutinesApi::class)
    private val viewModel: HomeViewModel by activityViewModels()
    private val controlador by lazy {
        findNavController()
    }
    private val adapter: ConnectDeviceAdapter by lazy {
        ConnectDeviceAdapter(requireContext(), RastherList())
    }
    private var macSelecionado = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    @OptIn(InternalCoroutinesApi::class, DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = getString(R.string.devices)

        viewModel.statusConnectionLiveData.observe(viewLifecycleOwner) {
            Log.i(this.javaClass.simpleName, "Status connection: $it")
            when(it.devicesStatus) {
                DevicesConnection.DevicesStatus.CONNECTING -> {
                    con_device_loading_spinner?.visibility = View.VISIBLE
                }
                DevicesConnection.DevicesStatus.CONNECTED -> {
                    con_device_loading_spinner?.visibility = View.INVISIBLE
                    controlador.popBackStack()
                }
                DevicesConnection.DevicesStatus.FAIL -> {
                    con_device_loading_spinner?.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), R.string.connect_error, Toast.LENGTH_LONG).show()
                }
                else -> { }
            }
        }
        viewModel.getDiscoveryStatus()?.observe(viewLifecycleOwner) {
            Log.i(this.javaClass.simpleName, it.toString())
            when(it) {
                DiscoveryStatus.FAIL -> {
                    con_device_loading_spinner?.visibility = View.INVISIBLE
                }
                DiscoveryStatus.FINISHED -> {
                    con_device_loading_spinner?.visibility = View.INVISIBLE
                    adapter.atualiza(viewModel.getDeviceList())
                }
                DiscoveryStatus.STARTED,
                DiscoveryStatus.RUNNING ->
                    con_device_loading_spinner?.visibility = View.VISIBLE
            }
        }

        return inflater.inflate(R.layout.fragment_connect_device, container, false)
    }

    @OptIn(InternalCoroutinesApi::class, DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.isConnected()) viewModel.disconnect()

        con_device_gvDevices.adapter = adapter

        setComponents()

        cdf_enable_bluetooth?.setOnClickListener {
            if (cdf_enable_bluetooth?.isChecked == true) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
            }
        }

        con_device_gvDevices.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                adapter.getItem(position)?.let {
                    macSelecionado = it.address
                    viewModel.connect(it.address)
                }
            }

        adapter.atualiza(viewModel.getDeviceList())
    }

    @OptIn(InternalCoroutinesApi::class, DelicateCoroutinesApi::class)
    private fun setComponents() {
        cdf_enable_bluetooth?.visibility = if (!viewModel.isBTEnabled()) View.VISIBLE else View.GONE
        con_device_imageBluetooth?.visibility = if (!viewModel.isBTEnabled()) View.VISIBLE else View.GONE
        con_device_gvDevices?.visibility = if (viewModel.isBTEnabled()) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_connect_device, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
    }

    @OptIn(InternalCoroutinesApi::class, DelicateCoroutinesApi::class)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh_device -> {
                if (viewModel.startDiscovery()) {
                    con_device_loading_spinner.visibility = View.VISIBLE
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @OptIn(InternalCoroutinesApi::class, DelicateCoroutinesApi::class)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i(this.javaClass.simpleName, "requestCode: $requestCode, resultCode: $resultCode")
        if (requestCode == REQUEST_ENABLE_BT) {
            cdf_enable_bluetooth?.isChecked = resultCode != 0
            setComponents()
            adapter.atualiza(viewModel.getDeviceList())
        }
    }

}