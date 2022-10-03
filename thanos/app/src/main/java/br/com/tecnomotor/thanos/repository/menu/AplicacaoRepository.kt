package br.com.tecnomotor.thanos.repository.menu

import br.com.tecnomotor.thanos.database.menu.MenuDatabase

class AplicacaoRepository (){
    companion object { // singleton
        @Volatile
        private var instance: AplicacaoRepository? = null

        //Singleton instance
        fun getInstance(): AplicacaoRepository {
            return instance ?: synchronized(this) {
                AplicacaoRepository().also { instance = it }
            }
        }
    }

    private val dao = MenuDatabase.getInstance().aplicacaoDao()

    fun getAplicacao(plaId: Long, verId: Long, verHabilitada: Long, monId: Long, veiId: Long,
                     motId: Long, tpsId: Long, sisId: Long, anoInicial: Long, anoFinal: Long, conId: Long) =
        dao.getAplicacao(plaId, verId, verHabilitada, monId, veiId, motId, tpsId, sisId, anoInicial, anoFinal, conId)
}