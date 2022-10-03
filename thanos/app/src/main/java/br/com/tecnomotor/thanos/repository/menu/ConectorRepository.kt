package br.com.tecnomotor.thanos.repository.menu

import br.com.tecnomotor.thanos.database.menu.MenuDatabase

class ConectorRepository {
    companion object { // singleton
        @Volatile
        private var instance: ConectorRepository? = null

        //Singleton instance
        fun getInstance(): ConectorRepository {
            return instance ?: synchronized(this) {
                ConectorRepository().also { instance = it }
            }
        }
    }

    private val dao = MenuDatabase.getInstance().conectorDao()

    fun getConectores(plaId: Long, verId: Long, verHabilitada: Long, monId: Long, veiId: Long,
                      motId: Long, tpsId: Long, sisId: Long,anoInicial: Long, anoFinal: Long) =
        dao.getByPlataformaVersao(plaId, verId, verHabilitada, monId, veiId, motId, tpsId, sisId,
            anoInicial, anoFinal)
}