package br.com.tecnomotor.thanos.ui.home.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import br.com.tecnomotor.rasther.Rasther
import br.com.tecnomotor.rasther.RastherRepository
import br.com.tecnomotor.thanos.config.ConfigDevice
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@DelicateCoroutinesApi
class HomeViewModel(): ViewModel() {

    private var repository = RastherRepository.getInstance()
    var statusConnectionLiveData = repository.statusConnectionLiveData
    var flagGoToMenu = false

    fun reCreateRepository(context: Context) {
        repository = RastherRepository.getInstance(context)
        statusConnectionLiveData = repository.statusConnectionLiveData
        repository.numberOfConnectionAttempts = ConfigDevice.getInstance().getNumberOfConnectionAttempts()
    }

    fun rasther() = repository.rasther

    fun initialization(demoMode: Boolean) {
        try {
            rasther().initialization(demoMode)
        } catch (e: Exception) {

        }
    }

    fun isBTEnabled():Boolean {
        return repository.isBTEnabled()
    }

    fun connect(address: String) {
        repository.connect(address)
    }

    fun disconnect() {
        repository.disconnect()
    }

    fun isConnected():Boolean {
        return try {
            rasther().isConnected()
        } catch (e: Exception) {
            false
        }
    }

    fun startDiscovery() = repository.startDiscovery()
    fun isDiscovering() = repository.isDiscovering()
    fun cancelDiscovery() = repository.cancelDiscovery()
    fun getDiscoveryStatus() = repository.getDiscoveryStatus()
    fun getDeviceList() = repository.getDeviceList()

}