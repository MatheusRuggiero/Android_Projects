package br.com.tecnomotor.thanos.repository.menu

import br.com.tecnomotor.thanos.database.menu.MenuDatabase

class VeiculoRepository() {

    companion object { // singleton
        @Volatile
        private var instance: VeiculoRepository? = null

        //Singleton instance
        fun getInstance(): VeiculoRepository {
            return instance ?: synchronized(this) {
                VeiculoRepository().also { instance = it }
            }
        }
    }

    private val dao = MenuDatabase.getInstance().veiculoDao()

    fun getVeiculos(plaId: Long, verId: Long, verHabilitada: Long, monId: Long) =
        dao.getVeiculos(plaId, verId, verHabilitada, monId)
}