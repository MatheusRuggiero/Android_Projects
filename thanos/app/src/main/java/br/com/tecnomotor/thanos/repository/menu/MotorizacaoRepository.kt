package br.com.tecnomotor.thanos.repository.menu


import br.com.tecnomotor.thanos.database.menu.MenuDatabase

class MotorizacaoRepository{

    companion object { // singleton
        @Volatile
        private var instance: MotorizacaoRepository? = null

        //Singleton instance
        fun getInstance(): MotorizacaoRepository {
            return instance ?: synchronized(this) {
                MotorizacaoRepository().also { instance = it }
            }
        }
    }

    private val dao = MenuDatabase.getInstance().motorizacaoDao()

    fun getMotorizacoes(plaId: Long, verId: Long, verHabilitada: Long, monId: Long,veiId: Long) =
        dao.getByPlataformaVersao(plaId, verId, verHabilitada, monId, veiId)
}