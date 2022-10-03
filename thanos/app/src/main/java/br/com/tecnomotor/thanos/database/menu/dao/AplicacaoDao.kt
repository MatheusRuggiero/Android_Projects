package br.com.tecnomotor.thanos.database.menu.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.tecnomotor.thanos.database.BaseDao
import br.com.tecnomotor.thanos.database.menu.entity.AplicacaoEntity

@Dao
interface AplicacaoDao : BaseDao<AplicacaoEntity> {

    @Query("SELECT DISTINCT A.* FROM APLICACAO A INNER JOIN PLATAFORMA_APLICACAO PA ON PA.APLID=A.APLID INNER JOIN VERSAO_APLICACAO VA ON VA.APLID=A.APLID INNER JOIN APLICACAO_CONECTOR AC ON AC.APLID=A.APLID WHERE ((PA.PLAID=:plaId) AND ((VA.VERID=:verId) OR (VA.VERID=:lastVerIdHabilitada)) AND (A.APLID>=0) AND (A.MONID=:monId) AND (A.VEIID=:veiId) AND ((A.MOTID=:motId) OR (A.MOTID is null)) AND (A.TPSID=:tpsId) AND (A.SISID=:sisId) AND ((A.APLANOINICIAL=:anoInicial) OR (A.APLANOINICIAL IS NULL)) AND ((A.APLANOFINAL=:anoFinal) OR (A.APLANOFINAL IS NULL)) AND (AC.CONID=:conId)) LIMIT 1")
    fun getAplicacao(plaId: Long, verId: Long, lastVerIdHabilitada: Long, monId: Long, veiId: Long, motId: Long, tpsId: Long, sisId: Long, anoInicial: Long, anoFinal: Long, conId: Long): LiveData<AplicacaoEntity>
}



