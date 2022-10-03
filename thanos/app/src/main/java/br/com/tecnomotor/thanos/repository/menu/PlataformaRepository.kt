package br.com.tecnomotor.thanos.repository.menu

import androidx.lifecycle.LiveData
import br.com.tecnomotor.thanos.database.menu.MenuDatabase
import br.com.tecnomotor.thanos.database.menu.entity.PlataformaEntity

class PlataformaRepository {

    companion object { // singleton
        @Volatile
        private var instance: PlataformaRepository? = null

        //Singleton instance
        fun getInstance(): PlataformaRepository {
            return instance ?: synchronized(this) {
                PlataformaRepository().also { instance = it }
            }
        }
    }

    private val dao = MenuDatabase.getInstance().plataformaDao()

    fun getPlataforma(plaId: Long):LiveData<PlataformaEntity> = dao.getById(plaId)
}