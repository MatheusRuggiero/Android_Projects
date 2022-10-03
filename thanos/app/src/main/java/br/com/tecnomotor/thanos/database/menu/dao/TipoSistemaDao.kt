package br.com.tecnomotor.thanos.database.menu.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.tecnomotor.thanos.database.BaseDao
import br.com.tecnomotor.thanos.database.menu.entity.TipoSistemaEntity

@Dao
interface TipoSistemaDao : BaseDao<TipoSistemaEntity> {
    @Query("SELECT DISTINCT T.* FROM APLICACAO A INNER JOIN PLATAFORMA_APLICACAO PA ON PA.APLID=A.APLID INNER JOIN VERSAO_APLICACAO VA ON VA.APLID=A.APLID INNER JOIN TIPOSISTEMA T ON T.TPSID=A.TPSID WHERE ((PA.PLAID=:plaId) AND ((VA.VERID=:verId) OR (VA.VERID=:lastVerIdHabilitada)) AND (A.APLID>=0) AND (A.MONID=:monId) AND (A.VEIID=:veiId) AND ((A.MOTID=:motId) OR (A.MOTID is null))) ORDER BY T.TPSNOME ASC")
    fun getByPlataformaVersao(plaId: Long, verId: Long, lastVerIdHabilitada: Long, monId: Long, veiId: Long, motId: Long): LiveData<MutableList<TipoSistemaEntity>>
}