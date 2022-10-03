package br.com.tecnomotor.thanos.repository.menu

import br.com.tecnomotor.thanos.database.menu.MenuDatabase

class TipoSistemasRepository{

    companion object { // singleton
        @Volatile
        private var instance: TipoSistemasRepository? = null

        //Singleton instance
        fun getInstance(): TipoSistemasRepository {
            return instance ?: synchronized(this) {
                TipoSistemasRepository().also { instance = it }
            }
        }
    }

    private val dao = MenuDatabase.getInstance().tipoSistemaDao()

    fun getTipoSistemas(plaId: Long, verId: Long, verHabilitada: Long, monId: Long, veiId: Long, motId: Long) =
        dao.getByPlataformaVersao(plaId, verId, verHabilitada, monId, veiId, motId)
}