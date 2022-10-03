package br.com.tecnomotor.thanos.database.menu.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.tecnomotor.thanos.database.BaseDao
import br.com.tecnomotor.thanos.database.menu.entity.VersaoEntity

@Dao
interface VersaoDao : BaseDao<VersaoEntity> {
    @Query("SELECT * FROM VERSAO")
    fun all(): LiveData<MutableList<VersaoEntity>>

    @Query("SELECT * FROM VERSAO V WHERE V.VERID=:verId")
    fun getById(verId: Long): LiveData<VersaoEntity>

    @Query("SELECT VA.VERID FROM VERSAO_APLICACAO VA INNER JOIN APLICACAO A ON A.APLID=VA.APLID INNER JOIN PLATAFORMA_APLICACAO PA ON PA.APLID=VA.APLID WHERE ((PA.PLAID=:plaId) AND (A.MONID=:monId)) ORDER BY VA.VERID ASC LIMIT 1")
    fun getVersaoByMontadora(plaId: Long, monId: Long): LiveData<VersaoEntity?>

    @Query("SELECT VA.VERID FROM VERSAO_APLICACAO VA INNER JOIN APLICACAO A ON A.APLID=VA.APLID INNER JOIN PLATAFORMA_APLICACAO PA ON PA.APLID=VA.APLID WHERE ((PA.PLAID=:plaId) AND (A.MONID=:monId) AND (A.VEIID=:veiId)) ORDER BY VA.VERID ASC LIMIT 1")
    fun getVersaoByVeiculo(plaId: Long, monId: Long, veiId: Long): LiveData<VersaoEntity?>

    @Query("SELECT VA.VERID FROM VERSAO_APLICACAO VA INNER JOIN APLICACAO A ON A.APLID=VA.APLID INNER JOIN PLATAFORMA_APLICACAO PA ON PA.APLID=A.APLID WHERE ((PA.PLAID=:plaId) AND (A.MONID=:monId) AND (A.VEIID=:veiId) AND (A.MOTID=:motId)) ORDER BY VA.VERID ASC LIMIT 1")
    fun getVersaoByMotorizacao(plaId: Long, monId: Long, veiId: Long, motId: Long): LiveData<VersaoEntity?>

    @Query("SELECT VA.VERID FROM VERSAO_APLICACAO VA INNER JOIN APLICACAO A ON A.APLID=VA.APLID INNER JOIN PLATAFORMA_APLICACAO PA ON PA.APLID=A.APLID WHERE ((PA.PLAID=:plaId) AND (A.MONID=:monId) AND (A.VEIID=:veiId) AND (A.MOTID=:motId) AND (A.TPSID=:tpsId)) ORDER BY VA.VERID ASC LIMIT 1")
    fun getVersaoByTipoSistema(plaId: Long, monId: Long, veiId: Long, motId: Long, tpsId: Long): LiveData<VersaoEntity?>

    @Query("SELECT VA.VERID FROM VERSAO_APLICACAO VA INNER JOIN APLICACAO A ON A.APLID=VA.APLID INNER JOIN PLATAFORMA_APLICACAO PA ON PA.APLID=A.APLID WHERE ((PA.PLAID=:plaId) AND (A.MONID=:monId) AND (A.VEIID=:veiId) AND (A.MOTID=:motId) AND (A.TPSID=:tpsId) AND (A.SISID=:sisId) AND ((A.APLANOINICIAL=:anoInicial) OR (A.APLANOINICIAL IS NULL)) AND ((A.APLANOFINAL=:anoFinal) OR (A.APLANOFINAL IS NULL))) ORDER BY VA.VERID ASC LIMIT 1")
    fun getVersaoBySistema(plaId: Long, monId: Long, veiId: Long, motId: Long, tpsId: Long, sisId: Long, anoInicial: Long, anoFinal: Long): LiveData<VersaoEntity?>

    @Query("SELECT VA.VERID FROM VERSAO_APLICACAO VA INNER JOIN APLICACAO A ON A.APLID=VA.APLID INNER JOIN PLATAFORMA_APLICACAO PA ON PA.APLID=A.APLID INNER JOIN APLICACAO_CONECTOR AC ON AC.APLID=A.APLID WHERE ((PA.PLAID=:plaId) AND (A.MONID=:monId) AND (A.VEIID=:veiId) AND (A.MOTID=:motId) AND (A.TPSID=:tpsId) AND (A.SISID=:sisId) AND ((A.APLANOINICIAL=:anoInicial) OR (A.APLANOINICIAL IS NULL)) AND ((A.APLANOFINAL=:anoFinal) OR (A.APLANOFINAL IS NULL)) AND (AC.CONID=:conId)) ORDER BY VA.VERID ASC LIMIT 1")
    fun getVersaoByConector(plaId: Long, monId: Long, veiId: Long, motId: Long, tpsId: Long, sisId: Long, anoInicial: Long, anoFinal: Long, conId: Long): LiveData<VersaoEntity?>
}