package br.com.tecnomotor.thanos.repository.menu

import android.util.Log
import androidx.lifecycle.LiveData
import br.com.tecnomotor.thanos.database.menu.MenuDatabase
import br.com.tecnomotor.thanos.database.menu.entity.MontadoraEntity

class MontadoraRepository() {

    companion object { // singleton
        @Volatile
        private var instance: MontadoraRepository? = null

        //Singleton instance
        fun getInstance(): MontadoraRepository {
            return instance ?: synchronized(this) {
                MontadoraRepository().also { instance = it }
            }
        }
    }

    private val dao = MenuDatabase.getInstance().montadoraDao()

    fun getMontadoras(plaId: Long, verId: Long, verHabilitada: Long): LiveData<MutableList<MontadoraEntity>> {
        return dao.getByPlataformaVersao(plaId, verId, verHabilitada)
    }
}