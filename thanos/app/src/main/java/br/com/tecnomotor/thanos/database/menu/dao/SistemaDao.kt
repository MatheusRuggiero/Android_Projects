package br.com.tecnomotor.thanos.database.menu.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.tecnomotor.thanos.database.menu.entity.SistemaAnoEntity

@Dao
interface SistemaDao {
    @Query("SELECT DISTINCT S.SISID,S.SISNOME,S.SISSPA,S.SISENG,A.APLANOINICIAL,A.APLANOFINAL FROM APLICACAO A INNER JOIN PLATAFORMA_APLICACAO PA ON PA.APLID=A.APLID INNER JOIN VERSAO_APLICACAO VA ON VA.APLID=A.APLID INNER JOIN SISTEMA S ON S.SISID=A.SISID WHERE ((PA.PLAID=:plaId) AND ((VA.VERID=:verId) OR (VA.VERID=:lastVerIdHabilitada)) AND (A.APLID>=0) AND (A.MONID=:monId) AND (A.VEIID=:veiId) AND ((A.MOTID=:motId) OR (A.MOTID is null)) AND (A.TPSID=:tpsId)) ORDER BY S.SISNOME ASC")
    fun getByPlataformaVersao(plaId: Long, verId: Long, lastVerIdHabilitada: Long, monId: Long, veiId: Long, motId: Long, tpsId: Long): LiveData<MutableList<SistemaAnoEntity>>
}