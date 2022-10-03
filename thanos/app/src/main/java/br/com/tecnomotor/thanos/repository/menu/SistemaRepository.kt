package br.com.tecnomotor.thanos.repository.menu

import br.com.tecnomotor.thanos.database.menu.MenuDatabase

class SistemaRepository {

    companion object { // singleton
        @Volatile
        private var instance: SistemaRepository? = null

        //Singleton instance
        fun getInstance(): SistemaRepository {
            return instance ?: synchronized(this) {
                SistemaRepository().also { instance = it }
            }
        }
    }

    private val dao = MenuDatabase.getInstance().sistemaDao()

    fun getSistemas(plaId: Long, verId: Long, verHabilitada: Long, monId: Long, veiId: Long, motId: Long, tpsId: Long) =
        dao.getByPlataformaVersao(plaId, verId, verHabilitada, monId, veiId, motId, tpsId)
}