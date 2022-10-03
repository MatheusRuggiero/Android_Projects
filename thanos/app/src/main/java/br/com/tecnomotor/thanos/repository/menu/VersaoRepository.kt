package br.com.tecnomotor.thanos.repository.menu

import androidx.lifecycle.LiveData
import br.com.tecnomotor.thanos.database.menu.MenuDatabase
import br.com.tecnomotor.thanos.database.menu.entity.VersaoEntity

class VersaoRepository {

    companion object { // singleton
        @Volatile
        private var instance: VersaoRepository? = null

        //Singleton instance
        fun getInstance(): VersaoRepository {
            return instance ?: synchronized(this) {
                VersaoRepository().also { instance = it }
            }
        }
    }

    private val dao = MenuDatabase.getInstance().versaoDao()

    fun getVersao(verId: Long): LiveData<VersaoEntity> = dao.getById(verId)

    fun getVersaoByMontadora(plaId: Long, monId: Long): LiveData<VersaoEntity?> {
        return dao.getVersaoByMontadora(plaId, monId)
    }

    fun getVersaoByVeiculo(plaId: Long, monId: Long, veiId: Long): LiveData<VersaoEntity?> {
        return dao.getVersaoByVeiculo(plaId, monId, veiId)
    }

    fun getVersaoByMotorizacao(plaId: Long, monId: Long, veiId: Long, motId: Long): LiveData<VersaoEntity?> {
        return dao.getVersaoByMotorizacao(plaId, monId, veiId, motId)
    }

    fun getVersaoByTipoDeSistemas(plaId: Long, monId: Long, veiId: Long, motId: Long, tpsId: Long): LiveData<VersaoEntity?> {
        return dao.getVersaoByTipoSistema(plaId, monId, veiId, motId, tpsId)
    }

    fun getVersaoBySistemas(plaId: Long, monId: Long, veiId: Long, motId: Long, tpsId: Long, sisId: Long, anoInicial: Long, anoFinal: Long): LiveData<VersaoEntity?> {
        return dao.getVersaoBySistema(plaId, monId, veiId, motId, tpsId, sisId, anoInicial, anoFinal)
    }

    fun getVersaoByConector(plaId: Long, monId: Long, veiId: Long, motId: Long, tpsId: Long, sisId: Long, anoInicial: Long, anoFinal: Long, conid: Long): LiveData<VersaoEntity?> {
        return dao.getVersaoByConector(plaId, monId, veiId, motId, tpsId, sisId, anoInicial, anoFinal, conid)
    }
}