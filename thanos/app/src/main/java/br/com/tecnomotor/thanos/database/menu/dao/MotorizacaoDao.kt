package br.com.tecnomotor.thanos.database.menu.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.tecnomotor.thanos.database.BaseDao
import br.com.tecnomotor.thanos.database.menu.entity.MotorizacaoEntity
import br.com.tecnomotor.thanos.database.menu.entity.VersaoEntity

@Dao
interface MotorizacaoDao : BaseDao<MotorizacaoEntity> {
    @Query("SELECT DISTINCT MT.* FROM APLICACAO A INNER JOIN PLATAFORMA_APLICACAO PA ON PA.APLID=A.APLID INNER JOIN VERSAO_APLICACAO VA ON VA.APLID=A.APLID LEFT JOIN MOTORIZACAO MT ON MT.MOTID=A.MOTID WHERE ((PA.PLAID=:plaId) AND ((VA.VERID=:verId) OR (VA.VERID=:lastVerIdHabilitada)) AND (A.APLID>=0) AND (A.MONID=:monId) AND (A.VEIID=:veiId)) ORDER BY MT.MOTNOME ASC")
    fun getByPlataformaVersao(plaId: Long, verId: Long, lastVerIdHabilitada: Long, monId: Long, veiId: Long): LiveData<MutableList<MotorizacaoEntity>>
}


